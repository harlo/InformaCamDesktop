package org.witness.informa.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

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
		} catch (InterruptedException e) {}
		catch (ExecutionException e) {}
	}
	
	private void context() {
		this.context = new JpegWrapper();
	}
	

	@Override
	public void extractMetadata(String md) {
		informa = (JSONObject) new JSONTokener(md).nextValue();
		
	}

	@Override
	public void extractMetadata() {
		
	}
	
}
