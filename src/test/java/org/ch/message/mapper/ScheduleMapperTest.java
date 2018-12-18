package org.ch.message.mapper;

import java.util.List;

import org.ch.message.model.Schedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
//@ActiveProfiles("dev")
public class ScheduleMapperTest {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleMapperTest.class);
	
	@Autowired
	private ScheduleMapper scheduleMapper;
	
	@Test
	public void testSelectSchedules() {
		
	List<Schedule> results = scheduleMapper.selectSchedules(1);
	
	for (Schedule schedule : results) {
		logger.debug("schedule seq: " + schedule.getCalendarSeq());
		logger.debug("calendar name: " + schedule.getCalendar().getCalendarName());
	}
	}
}
