package org.witness.informa.utils;

import java.io.File;


import net.sf.json.JSONObject;

import org.witness.informa.utils.Informa;
import org.witness.informa.wrappers.JpegWrapper;

public class InformaImage extends File implements Informa {
	private static final long serialVersionUID = 8720438769021179332L;
	
	JSONObject metadata;
	
	public JpegWrapper context;


	public InformaImage(String path) {
		super(path);
		
		System.out.print("informa image starting");	
		context();
		//jpeg.getMetadata(this.getAbsolutePath());
		
		//getMetadata(this.getAbsolutePath());
	}
	
	private void context() {
		this.context = new JpegWrapper();
	}
	

	@Override
	public void extractMetadata() {
		//metadata = (JSONObject) new JSONTokener(getMetadata(this.getAbsolutePath())).nextValue();
		
	}
	
}
