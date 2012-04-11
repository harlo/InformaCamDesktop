package org.witness.informa.utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.witness.informa.utils.MediaLoader.MediaPicker;
import org.witness.informa.wrappers.FfmpegWrapper;

public class InformaVideo extends File implements Informa, Constants, InformaConstants {
	private static final long serialVersionUID = -4816032382750755566L;
	
	public JSONArray streamInfo;
	public JSONObject metadata;
	
	FfmpegWrapper ffmpeg;
	int mimeType;
	String videoRoot, videoPath;
	boolean hadMetadataOnLoad = false;
	
	public File clone;
	File mdFile;
	
	public InformaVideo(String path) throws InterruptedException, ExecutionException, JSONException {
		super(path);
		metadata = new JSONObject();
		context();
	}
	
	private void context() throws InterruptedException, ExecutionException, JSONException {
		this.ffmpeg = new FfmpegWrapper();
		
		setStreamInfo(ffmpeg.spawnCommand(Ffmpeg.SHOW_STREAMS.replace(Ffmpeg.Replace.VIDEO, this.getAbsolutePath())));
		this.videoRoot = getName().substring(0, this.getName().lastIndexOf("."));
		this.videoPath = this.getParent() + "/";
		
		// if the stream has a subtitle track...
		for(int s = 0; s<streamInfo.size(); s++) {
			JSONObject stream = streamInfo.getJSONObject(s);
			System.out.println(stream.toString());
			if(stream.getString(Ffmpeg.Tags.Stream.CODEC_TYPE).compareTo("subtitle") == 0) {
				hadMetadataOnLoad = true;
				extractMetadata();
			} else if(stream.getString(Ffmpeg.Tags.Stream.CODEC_TYPE).compareTo("video") == 0) {
				JSONObject dimensions = new JSONObject();
				dimensions.put(Media.Dimensions.WIDTH, stream.getString(Media.Dimensions.WIDTH));
				dimensions.put(Media.Dimensions.HEIGHT, stream.getString(Media.Dimensions.HEIGHT));
				metadata.put(Media.DIMENSIONS, dimensions);
			}
			
			// if it is an mkv, clone to mp4 with subtitles stripped after extraction...
			mimeType = MediaPicker.MapFileType(this.getName().substring(this.getName().lastIndexOf(".")));
			switch(mimeType) {
			case Media.MimeTypes.MKV:
				clone = new File(Ffmpeg.CLONE
							.replace(Ffmpeg.Replace.VIDEO_PATH, videoPath)
							.replace(Ffmpeg.Replace.VIDEO_ROOT, videoRoot)
						);
				ffmpeg.spawnCommand(Ffmpeg.CLONE_FROM_MKV
						.replace(Ffmpeg.Replace.VIDEO, this.getAbsolutePath())
						.replace(Ffmpeg.Replace.CLONE, clone.getAbsolutePath())
					);
				break;
			case Media.MimeTypes.MP4:
				clone = this.getAbsoluteFile();
				break;
			}
			
			metadata.put(Media.MEDIA_TYPE, Media.MediaTypes.VIDEO);
			metadata.put(Media.PATH, clone.getAbsolutePath());
		}
	}
	
	public void setStreamInfo(JSONObject streamInfo) throws JSONException {
		this.streamInfo = streamInfo.getJSONArray(Ffmpeg.Tags.RETURN);
	}
	
	@Override
	public void extractMetadata() {
		try {
			String mdFilePath = Ffmpeg.METADATA
					.replace(Ffmpeg.Replace.VIDEO_PATH, videoPath)
					.replace(Ffmpeg.Replace.VIDEO_ROOT, videoRoot);
			ffmpeg.spawnCommand(Ffmpeg.EXTRACT_INFORMA_TRACK
					.replace(Ffmpeg.Replace.VIDEO, this.getAbsolutePath())
					.replace(Ffmpeg.Replace.METADATA, mdFilePath)
				);
			this.mdFile = new File(mdFilePath);
			
			JSONArray timeblocks = new JSONArray();
			for(String s : MediaLoader.fileToStrings(mdFile)) {
				JSONObject timeblock = new JSONObject();
				
				timeblock.put(Keys.CaptureEvent.TIMESTAMP, s.substring(s.indexOf(s.split("alogue: 0,")[1]), s.indexOf(",DefaultVCD")));
				timeblock.put("captured", s.substring(s.indexOf("pos(400,570)}") + new String("pos(400,570)}").length()));
				timeblocks.add(timeblock);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getIntegrityRating() {
		// TODO: get integrity rating!
		return 100;
	}
	
	@Override
	public void extractMetadata(String md) {
		
	}

	@Override
	public File createClone(File dir, File original, String cloneName) {
		return original;
		
	}

}
