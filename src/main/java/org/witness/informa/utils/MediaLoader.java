package org.witness.informa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.witness.informa.utils.Constants.Media.MediaTypes;
import org.witness.informa.wrappers.FfmpegWrapper;
import org.witness.informa.wrappers.JpegWrapper;

import com.fourspaces.couchdb.*;

public class MediaLoader implements Constants {
	InformaVideo video;
	InformaImage image;
	
	public int mediaType = 0;
	
	public Database dbSubmissions, dbSources;
	public Document docSubmissions, docSources;
	
	public MediaLoader() {
		Session dbSession = new Session("localhost", 5984);
		dbSubmissions = dbSession.getDatabase("submissions");
		dbSources = dbSession.getDatabase("sources");
		
		try {
			docSubmissions = dbSubmissions.getDocument("_design/submissions");
			docSources = dbSources.getDocument("_design/sources");
		} catch(IOException e) {
			CouchParser.Log(Couch.ERROR, e.toString());
		}
	}
	
	private void initVideo(String path) throws Exception {
		video = new InformaVideo(path);
		image = null;
	}
	
	private void initImage(String path) throws Exception {
		image = new InformaImage(path);
		video = null;
	}
	
	public ArrayList<JSONObject> getSubmissions() {
		return CouchParser.getRows(dbSubmissions, docSubmissions, Couch.Views.Submissions.GET_BY_MEDIA_TYPE, new String[] {"hashed_pgp"});
	}
	
	public ArrayList<JSONObject> getSources() {
		ArrayList<JSONObject> sourcesList = new ArrayList<JSONObject>();
		
		return sourcesList;
	}
	
	public JSONObject loadMedia(String path) {
		JSONObject loadedData = new JSONObject();
		int mimeType = MediaPicker.MapFileType(path.substring(path.lastIndexOf(".")));
		switch(mimeType) {
		case Media.MimeTypes.JPEG:
			image = new InformaImage(path);
			mediaType = MediaTypes.IMAGE;
			loadedData = image.informa;
			break;
		case Media.MimeTypes.MP4:
			try {
				video = new InformaVideo(path);
			} catch (JSONException e) {
				CouchParser.Log(Couch.ERROR, e.toString());
			} catch (InterruptedException e) {
				CouchParser.Log(Couch.ERROR, e.toString());
			} catch (ExecutionException e) {
				CouchParser.Log(Couch.ERROR, e.toString());
			}
			mediaType = MediaTypes.VIDEO;
			break;
		case Media.MimeTypes.MKV:
			try {
				video = new InformaVideo(path);
			} catch (JSONException e) {
				CouchParser.Log(Couch.ERROR, e.toString());
			} catch (InterruptedException e) {
				CouchParser.Log(Couch.ERROR, e.toString());
			} catch (ExecutionException e) {
				CouchParser.Log(Couch.ERROR, e.toString());
			}
			mediaType = MediaTypes.VIDEO;
			loadedData = video.metadata;
			break;
		}
		
		
		return loadedData;
	}
	
	public static ArrayList<String> fileToStrings(File file) throws IOException {
		ArrayList<String> fStrings = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String line;
		while((line = br.readLine()) != null)
			fStrings.add(line);
		return fStrings;
	}
	
	public static class MediaPicker extends FileFilter implements Constants {
		public MediaPicker() {}

		@Override
		public boolean accept(File file) {
			if(file.isDirectory())
				return true;
			else {
				String path = file.getAbsolutePath().toLowerCase();
				for(int i=0, n = Media.EXTENSIONS.length; i < n; i++) {
					String extension = Media.EXTENSIONS[i];
					if(path.endsWith(extension)) {
						return true;
					}
				}
				return false;
			}
		}

		@Override
		public String getDescription() {
			return UI.Prompt.MEDIA_PICKER;
		}
		
		public static int MapFileType(String extension) {
			int mimeType = -1;
			Iterator<Entry<String, Integer>> i = Media.MIME_TYPES.entrySet().iterator();
			while(i.hasNext()) {
				Entry<String, Integer> e = (Entry<String, Integer>) i.next();
				if(e.getKey().equalsIgnoreCase(extension))
					mimeType = e.getValue();
			}
			return mimeType;
			
		}
	}
}
