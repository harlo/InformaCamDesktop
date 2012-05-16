package org.witness.informa.utils;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.witness.informa.utils.Constants.DC.Commands;
import org.witness.informa.utils.Constants.DC.Keys;

public class InformaMessage implements Constants {
	public Map<String, Object> in;
	public Map<String, Object> out;
	public Map<String, Object> opts;
	
	public static Map<String, Object> in(Message msg) {
		Map<String, Object> in = msg.getDataAsMap();
        return in;
	}
	
	public static Map<String, Object> spinner(String spinnerText, int codeToWaitFor) {
		Map<String, Object> spinner = new HashMap<String, Object>();
		spinner.put(Keys.INTERRUPT, Commands.WAIT_FOR_PROCESS);
		spinner.put(Keys.WAIT_CODE, codeToWaitFor);
		spinner.put(Keys.MESSAGE, spinnerText);
		Map<String, Object> output = new HashMap<String, Object>();
		output.put(DC.RESPONSE, spinner);
		return output;
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
		if(this.in.containsKey(DC.Keys.OPTIONS))
			this.opts = (Map<String, Object>) this.in.get(DC.Keys.OPTIONS);
		else
			this.opts = null;
		System.out.println("INPUT: " + this.in.toString());
	}
}
