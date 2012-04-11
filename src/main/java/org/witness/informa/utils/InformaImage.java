package org.witness.informa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import org.witness.informa.DesktopService;
import org.witness.informa.utils.Informa;
import org.witness.informa.wrappers.JpegWrapper;

public class InformaImage extends File implements Informa, InformaConstants {
	private static final long serialVersionUID = 8720438769021179332L;
	
	JSONObject informa;
	
	public JpegWrapper context;


	public InformaImage(String path) {
		super(path);
		
		context();
		Map<String, String> command = new HashMap<String, String>();
		command.put(Callback.Jpeg.GET_METADATA, getAbsolutePath());
		try {
			extractMetadata(context.spawnCommand(command).getString(Callback.Jpeg.GET_METADATA));
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		catch (ExecutionException e) {
			System.out.println(e.toString());
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
		createClone();
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
	public void createClone() {
		File clone = new File(CACHE_ROOT, this.getName());
		try {
			FileChannel orig = new FileInputStream(this).getChannel();
			FileChannel copy = new FileOutputStream(clone).getChannel();
			copy.transferFrom(orig, 0, orig.size());
		} catch(IOException e) {
			DesktopService.Log(e.toString());
		}
		
	}
	
}
