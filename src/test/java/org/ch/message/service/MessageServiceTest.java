package org.ch.message.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
//@ActiveProfiles("dev")
public class MessageServiceTest {

	@Autowired
	private MessageService messageService;
	
	@Test
	public void testSend() {
		messageService.scheduledSend();
	}
	
	@Test
	public void testReplace() {
		String contents = "{{title}}\r\n" + 
				"안녕하세요. {{name}} {{grade}}!\r\n" + 
				"안수집사회입니다.\r\n" + 
				"내일{{schedule_date}} 새벽기도차량운행차례입니다.\r\n" + 
				"8115차량으로 운행부탁드립니다.\r\n" + 
				"새벽을 깨우는 안수집사회!!!!!!!!!!!!!!!!!!!";
		
		String replaced= contents.replaceAll("\\{\\{title\\}\\}", "타이틀");
		
		System.out.println(replaced);
	}
}
