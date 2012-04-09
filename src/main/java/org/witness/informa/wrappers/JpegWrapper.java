package org.witness.informa.wrappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.json.JSONObject;

import org.witness.informa.utils.Constants;
import org.witness.informa.utils.InformaConstants;

public class JpegWrapper implements Constants, InformaConstants {
	public native String getMetadata(String filename);
	public native byte[] unpackRegion(String imageFilename, int regionStart, int regionLength);
	public native String sayHi();
	
	ArrayList<Map<String, String>> commands;
	ExecutorService ex;
	public JpegWrapper wrapper;
	
	static {
		System.load(APP_ROOT + "jni/libJpegWrapper.so");
	}
	
	public JpegWrapper() {
		ex = Executors.newFixedThreadPool(100);
		commands = new ArrayList<Map<String, String>>();
		wrapper = this;
	}
	
	public JSONObject spawnCommand(Map<String, String> command) throws InterruptedException, ExecutionException {
		commands.add(command);
		
		Future<JSONObject> cmd = ex.submit(new JpegThread());
		return cmd.get();
	}
	
	private class JpegThread implements Callable<JSONObject> {

		@Override
		public JSONObject call() throws Exception {
			JSONObject result = new JSONObject();
			
			Map<String, String> command = commands.get(0);
			String method = command.keySet().iterator().next();
			String[] params = command.get(method).split(",");
			
			if(method.equals(Callback.Jpeg.GET_METADATA)) {
				result.put(method, wrapper.getMetadata(params[0]));
			}
			
			System.out.println(result.toString());
			commands.remove(command);
			return result;
		}
		
	}
}