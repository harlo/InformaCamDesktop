package org.witness.informa.wrappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.witness.informa.utils.Constants;

public class FfmpegWrapper implements Constants {
	ArrayList<String> commands;
	public ArrayList<JSONObject> results;
	public FfmpegWrapper wrapper;
	ExecutorService ex;
	
	public FfmpegWrapper() {
		commands = new ArrayList<String>();
		results = new ArrayList<JSONObject>();
		
		ex = Executors.newFixedThreadPool(100);
		
		wrapper = this;
	}
	
	public void addResult(JSONObject result) {
		synchronized(results) {
			results.add(result);
		}
	}
	
	public JSONObject spawnCommand(String command) throws InterruptedException, ExecutionException {
		commands.add(command);
		System.out.println(command);
		Future<JSONObject> cmd = ex.submit(new FfmpegThread());
		
		return cmd.get();
	}
	
	public String matchTag(String t) {
		String matchedTag = null;
		Iterator<Entry<String, String>> i = Ffmpeg.TAGS.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, String> e = (Entry<String, String>) i.next();
			if(e.getKey().equalsIgnoreCase(t))
				matchedTag = e.getValue();
		}
		return matchedTag;
	}
	
	public JSONObject jsonify(ArrayList<String> output, int pid) throws JSONException {
		JSONObject json = new JSONObject();
		json.put(Ffmpeg.PID, pid);
		
		ArrayList<JSONObject> entities = new ArrayList<JSONObject>();
		for(String o : output) {
			if(o.contains("[") && !(o.contains("[/")) && !(o.contains("codec_tag_string"))) {
				// this is a tag
				JSONObject entity = new JSONObject();
				entity.put(Ffmpeg.Tags.TYPE, matchTag(o));
				entities.add(entity);
				
			} else if(o.contains("="))
				entities.get(entities.size() - 1).put(o.split("=")[0], o.split("=")[1]);
		}
		json.put(Ffmpeg.Tags.RETURN, entities);
		return json;
	}
	
	
	private class FfmpegThread implements Callable<JSONObject> {
		InputStream is;
		
		@Override
		public JSONObject call() {
			
			try {
				Process fp = Runtime.getRuntime().exec(commands.get(0));
				System.out.println(LOG + commands.get(0));
				is = fp.getInputStream();
				
				commands.remove(0);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = "";
				ArrayList<String> output = new ArrayList<String>();
				while((line = br.readLine()) != null)
					output.add(line);
				
				JSONObject result = jsonify(output, results.size());
				addResult(result);
				return result;
								
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
