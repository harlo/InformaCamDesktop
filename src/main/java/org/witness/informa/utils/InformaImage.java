package org.witness.informa.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.imgscalr.AsyncScalr;
import org.witness.informa.DesktopService;
import org.witness.informa.utils.Informa;
import org.witness.informa.utils.InformaConstants.Keys.Data;
import org.witness.informa.utils.InformaConstants.Keys.ImageRegion;
import org.witness.informa.wrappers.JpegWrapper;

public class InformaImage extends File implements Informa, InformaConstants {
	private static final long serialVersionUID = 8720438769021179332L;
	
	JSONObject informa;
	
	public JpegWrapper context;

	public InformaImage(String path) {
		super(path);
		
		Log(TAG, "HELLO INFORMA IMAGE!");
		
		context();
		Map<String, ArrayList<Object>> command = new HashMap<String, ArrayList<Object>>();
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(getAbsolutePath());
		command.put(Callback.Jpeg.GET_METADATA, args);
		try {
			extractMetadata(context.spawnCommand(command).getString(Callback.Jpeg.GET_METADATA));
			createUnredactedImage();
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		} catch (ExecutionException e) {
			DesktopService.Log(e.toString());
		} catch (DecoderException e) {
			DesktopService.Log(e.toString());
		} catch (IOException e) {
			DesktopService.Log(e.toString());
		}
	}
	
	private void context() {
		this.context = new JpegWrapper();
	}
	
	private JSONObject bruteForceMetadata(String md) {
		String salvage = md.substring(0, md.lastIndexOf("}}") + 2);
		salvage = salvage.replace("\\", "");		
		return (JSONObject) new JSONTokener(salvage).nextValue();
	}
	
	private void createUnredactedImage() throws DecoderException, IOException, InterruptedException, ExecutionException {
		JSONArray imageRegions = informa.getJSONObject(Keys.Informa.DATA).getJSONArray(Data.IMAGE_REGIONS);
		
		File clone = new File(CACHE_ROOT, getName().replace(".jpg", "_unredacted.jpg"));
		BufferedImage copyTemplate = ImageIO.read(this);
		Graphics2D g2D = copyTemplate.createGraphics();
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.drawImage(copyTemplate, 0, 0, null);
		
		for(int i = 0; i < imageRegions.size(); i++) {
			JSONObject ir = imageRegions.getJSONObject(i);
			
			if(ir.getString(ImageRegion.FILTER).compareTo(ImageRegion.Filter.IDENTIFY) != 0) {
				char[] regionDataString = ((String) ir.getJSONObject(ImageRegion.UNREDACTED_DATA).remove(ImageRegion.Data.BYTES)).toCharArray();
				byte[] regionData = Base64.decodeBase64(Hex.decodeHex(regionDataString));
				
				Map<String, ArrayList<Object>> command = new HashMap<String, ArrayList<Object>>();
				ArrayList<Object> args = new ArrayList<Object>();
				args.add(getAbsolutePath());
				args.add(CACHE_ROOT + getName().replace(".jpg", i + ".jpg"));
				args.add(regionData);
				args.add(regionData.length);
				command.put(Callback.Jpeg.REVERSE_REDACTION, args);
				
				if(context.spawnCommand(command).getInt(Callback.Jpeg.REVERSE_REDACTION) == 1) {
					JSONObject dims = ir.getJSONObject(ImageRegion.DIMENSIONS);
					JSONObject coords = ir.getJSONObject(ImageRegion.COORDINATES);
					
					BufferedImage bareClone = ImageIO.read(new File((String) args.get(1)));
					
					int left = (coords.getInt(ImageRegion.LEFT) > 0) ? coords.getInt(ImageRegion.LEFT) : 0;
					int top = (coords.getInt(ImageRegion.TOP) > 0) ? coords.getInt(ImageRegion.TOP) : 0;
					int width = (dims.getInt(ImageRegion.WIDTH) > 0) ? dims.getInt(ImageRegion.WIDTH) : 0;
					int height = (dims.getInt(ImageRegion.HEIGHT) > 0) ? dims.getInt(ImageRegion.HEIGHT) : 0;
					
					BufferedImage croppedRegion = AsyncScalr.crop(
							bareClone, 
							left, 
							top, 
							width,
							height).get();
					
					g2D.drawImage(croppedRegion, coords.getInt(ImageRegion.LEFT), coords.getInt(ImageRegion.TOP), null);
				}
			}
		}
		
		g2D.dispose();
		ImageIO.write(copyTemplate, "jpg", clone);
		//createClone(new File(CACHE_ROOT), clone, clone.getName());
		informa.put(Keys.Image.DIMENSIONS, new int[] {copyTemplate.getWidth(), copyTemplate.getHeight()});
		
	}

	@Override
	public void extractMetadata(String md) {
		try {
			informa = (JSONObject) new JSONTokener(md).nextValue();
			
		} catch(JSONException e) {
			/* this means that the memory allocated some gobbitygook.
			 * we can try to create the json object with brute force 
			 */
			
			informa = bruteForceMetadata(md);
		}
		
		informa.put(Media.LOCAL_PATH, getAbsolutePath());
		informa.put(Keys.Device.INTEGRITY, getIntegrityRating());
		createClone(new File(CACHE_ROOT), this, this.getName());
	}

	@Override
	public void extractMetadata() {
		
	}
	
	@Override
	public int getIntegrityRating() {
		// TODO: get integrity rating!
		return 100;
	}
	
	@Override
	public File createClone(File dir, File original, String cloneName) {
		File clone = new File(dir, cloneName);
		try {
			FileChannel orig = new FileInputStream(original).getChannel();
			FileChannel copy = new FileOutputStream(clone).getChannel();
			copy.transferFrom(orig, 0, orig.size());
		} catch(IOException e) {
			DesktopService.Log(e.toString());
		}
		return clone;
	}
	
	private void Log(String tag, String msg) {
		System.out.println("***** " + tag + " ******: " + msg);
	}
}
