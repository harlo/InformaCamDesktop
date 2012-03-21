package org.witness.informa.utils;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;

public class InformaMessage implements Constants {
	public Map<String, Object> in;
	public Map<String, Object> out;
	
	public static Map<String, Object> in(Message msg) {
		Map<String, Object> in = msg.getDataAsMap();
        return in;
	}
	
	public void put(String tag, Object input) {
		if(this.out == null)
			this.out = new HashMap<String, Object>();
		
		this.out.put(tag, input);
	}
	
	public Map<String, Object> out() {
		Map<String, Object> output = new HashMap<String, Object>();
		output.put(DC.RESPONSE, this.out);
		System.out.println("RESPNOSE: " + output.toString());
		return output;
	}
	
	public InformaMessage(Message msg) {
		this.in = in(msg);
		System.out.println("INPUT: " + this.in.toString());
	}
}
