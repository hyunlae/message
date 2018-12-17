package org.ch.message;

import org.ch.message.common.util.MessageUtil;
import org.json.simple.JSONObject;
import org.junit.Test;

public class MessageUtilTest {

	
	@Test
	public void testSendSms() {
		
		String to = "01066965116";
		String from = "01066965116";
		String text = "차량운행";
		String datetime = "201812170858";
		
		JSONObject result = MessageUtil.sendSms(to, from, text, datetime);
		System.out.println(result.toString());
	}
}
