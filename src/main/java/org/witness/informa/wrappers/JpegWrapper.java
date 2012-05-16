package org.witness.informa.wrappers;

import java.util.ArrayList;
import java.util.Map;
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
	public native int unpackRegion(String imageFilename, String cloneFileName, byte[] regionData, int regionDataLength);
	
	ArrayList<Map<String, ArrayList<Object>>> commands;
	ExecutorService ex;
	public JpegWrapper wrapper;
	
	static {
		System.load(APP_ROOT + "jni/libJpegWrapper.so");
	}
	
	public JpegWrapper() {
		ex = Executors.newFixedThreadPool(100);
		commands = new ArrayList<Map<String, ArrayList<Object>>>();
		wrapper = this;
	}
	
	public JSONObject spawnCommand(Map<String, ArrayList<Object>> command) throws InterruptedException, ExecutionException {
		commands.add(command);
		
		Future<JSONObject> cmd = ex.submit(new JpegThread());
		return cmd.get();
	}
	
	private class JpegThread implements Callable<JSONObject> {

		@Override
		public JSONObject call() throws Exception {
			JSONObject result = new JSONObject();
			
			Map<String, ArrayList<Object>> command = commands.get(0);
			String method = command.keySet().iterator().next();
			ArrayList<Object> args = command.get(method);
			
			if(method.equals(Callback.Jpeg.GET_METADATA)) {
				result.put(method, wrapper.getMetadata((String) args.get(0)));
			} else if(method.equals(Callback.Jpeg.REVERSE_REDACTION)) {
				result.put(method, wrapper.unpackRegion(
						(String) args.get(0),
						(String) args.get(1),
						(byte[]) args.get(2),
						(Integer) args.get(3)
					)
				);
			}
			
			System.out.println(result.toString());
			commands.remove(command);
			return result;
		}
		
	}
}