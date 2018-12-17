package org.ch.message.common.util;

import java.util.HashMap;

import org.json.simple.JSONObject;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class MessageUtil {

	private final static String api_key = "NCSAL3MLMSJF0BWC";
	private final static String api_secret = "O9M9OW3XPEY4MGCAEYYJOLY4QGGYAYB2";
	
	/**
	 * 
	 * @param to: 수신번호
	 * @param from: 발신번호
	 * @param text: 문자내용
	 * @param datetime: 
	 * @return
	 */
	public static JSONObject sendSms(String to, String from, String text, String datetime) {
		
		JSONObject obj = null;
		
		Message coolsms = new Message(api_key, api_secret);

	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", "01066965116");
	    params.put("from", "01066965116");
	    params.put("type", "SMS");
	    params.put("text", text);
	    params.put("app_version", "test app 1.2"); // application name and version
//	    params.put("app_version", "JAVA SDK v1.2"); // application name and version
	    
	    if(!isEmpty(datetime)) {
	    	params.put("datetime", datetime); // Format must be(YYYYMMDDHHMISS) 2016-02-21 15:00:00
	    }
	    
	    try {
	      obj = (JSONObject) coolsms.send(params);
//	      System.out.println(obj.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
	    
		return obj;
	}

	private static boolean isEmpty(String datetime) {
		return "".equals(datetime) || datetime == null;  
	}

}
