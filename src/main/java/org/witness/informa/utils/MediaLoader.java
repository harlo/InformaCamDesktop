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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import net.sf.json.JSONObject;

import org.witness.informa.utils.Constants.Media.MediaTypes;
import org.witness.informa.wrappers.FfmpegWrapper;
import org.witness.informa.wrappers.JpegWrapper;

public class MediaLoader implements Constants {
	InformaVideo video;
	InformaImage image;
	
	public int mediaType = 0;
	
	public MediaLoader() {}
	
	private void initVideo(String path) throws Exception {
		video = new InformaVideo(path);
		image = null;
	}
	
	private void initImage(String path) throws Exception {
		image = new InformaImage(path);
		video = null;
	}
	
	public JSONObject loadMedia() throws Exception {
		JSONObject loadedData = new JSONObject();
		// open file chooser
		JFileChooser chooser = new JFileChooser(Media.PATH_START);
		chooser.setFileFilter(new MediaPicker());
		int choice = chooser.showOpenDialog(null);
		
		if(choice == JFileChooser.APPROVE_OPTION) {
			int mimeType = MediaPicker.MapFileType(chooser.getSelectedFile().getName().substring(chooser.getSelectedFile().getName().lastIndexOf(".")));
			switch(mimeType) {
			case Media.MimeTypes.JPEG:
				image = new InformaImage(chooser.getSelectedFile().getAbsolutePath());
				mediaType = MediaTypes.IMAGE;
				loadedData = image.informa;
				break;
			case Media.MimeTypes.MP4:
				video = new InformaVideo(chooser.getSelectedFile().getAbsolutePath());
				mediaType = MediaTypes.VIDEO;
				break;
			case Media.MimeTypes.MKV:
				video = new InformaVideo(chooser.getSelectedFile().getAbsolutePath());
				mediaType = MediaTypes.VIDEO;
				loadedData = video.metadata;
				break;
			}
			
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