package org.ch.message.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ch.message.common.util.MessageUtil;
import org.ch.message.mapper.MessageMapper;
import org.ch.message.mapper.ScheduleMapper;
import org.ch.message.model.Message;
import org.ch.message.model.Schedule;
import org.ch.message.service.MessageService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	private final static String FROM = "01066965116";

	@Autowired
	private ScheduleMapper scheduleMapper;

	@Autowired
	private MessageMapper messageMapper;

	@Scheduled(cron = "0 22 12 * * * ")
	@Override
	public void scheduledSend() {

		SimpleDateFormat sdf = new SimpleDateFormat("MM월dd일(E)");

		/**
		 * 내일 일정을 가지고 와서, 오늘 미리 예약문자를 발생 시킨다.
		 */
		List<Schedule> schedules = scheduleMapper.selectSchedules(1);

		for (Schedule schedule : schedules) {

			String notification = schedule.getNotifications();
			String title = "[" + schedule.getCalendar().getCalendarName() + "]";
			String content = schedule.getScheduleContent();
			Date scheduleDate = schedule.getScheduleDate();
			JsonParser parser = new JsonParser();
			JsonObject noti = parser.parse(notification).getAsJsonObject();
			JsonArray receivers = noti.has("msg") ? noti.getAsJsonArray("msg") : null;
			int sendCount = 0;
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR, 1);
			Date reservedTime = cal.getTime();
			
			logger.debug("schedule date: " + schedule.getScheduleDate());
			logger.debug("notifications : " + noti.toString());
			logger.debug("receivers : " + receivers.toString());

			for (JsonElement receiverEl : receivers) {

				JsonObject receiver = receiverEl.getAsJsonObject();
				receiver.getAsJsonObject();
				String name = receiver.get("name").getAsString();
				String grade = receiver.get("grade").getAsString();
				String mobile = receiver.get("mobile").getAsString();
				String to = mobile;

				content = content.replaceAll("\\{\\{name\\}\\}", name);
				content = content.replaceAll("\\{\\{grade\\}\\}", grade);
				content = content.replaceAll("\\{\\{title\\}\\}", title);
				content = content.replaceAll("\\{\\{schedule_date\\}\\}",
						"(" + sdf.format(schedule.getScheduleDate()) + ")");

				logger.debug("receiver name: " + name);
				logger.debug("receiver grade : " + grade);
				logger.debug("receiver mobile : " + mobile);
				logger.debug("calendar name : " + schedule.getCalendar().getCalendarName());
				logger.debug("content: " + content);

				Message record = new Message();
				record.setSender(FROM);
				record.setReciever(to);
				record.setText(content);
				
				record.setReservedTime(reservedTime);
				sendCount += messageMapper.insertSelective(record);

			}

			logger.info("sendCount: " + sendCount);

		}

	}

	/**
	 * 5초마다 메세지를 보낸다.
	 * message 테이블에서 조회되는 문자를 보낸다.
	 */
	@Scheduled(fixedDelay = 5000)
	public void batchSend() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		int state = 0;
		List<Message> messages = messageMapper.selectMessagesByState(state);

		for (Message message : messages) {
			
			Message record = new Message();
			
			try {
				String content = message.getText();
				String to = message.getReciever();
				String formattedReservedTime = message.getReservedTime() !=null ? sdf.format(message.getReservedTime()): null ;
	//			String formattedReservedTime = sdf.format(reservedTime);
	
				String type = message.getMessageType();
				JSONObject result = MessageUtil.send(type, to, FROM, content, formattedReservedTime);
				String groupId = result.containsKey("success_count") && (long)result.get("success_count") == 1 ? (String) result.get("group_id"): null;
				logger.debug("result: ", result.toString());
				
				record.setMessasgeSeq(message.getMessasgeSeq());
				record.setGroupId(groupId);
				
			}catch (Exception e) {
				
				e.printStackTrace();
				record.setMessasgeSeq(message.getMessasgeSeq());
				
				
			} finally {
				record.setState(1);
				int res = messageMapper.updateByPrimaryKeySelective(record);	
			}
			
		}
	}
	
	
//	@Scheduled(fixedDelay = 10000)
//	public void batchGetResult() {
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//
//		int state = 1;
//		List<Message> messages = messageMapper.selectMessagesByState(state);
//
//		for (Message message : messages) {
//			
//			String content = message.getText();
//			String to = message.getReciever();
//			String formattedReservedTime = message.getReservedTime() !=null ? sdf.format(message.getReservedTime()): null ;
////			String formattedReservedTime = sdf.format(reservedTime);
//
//			String type = message.getMessageType();
//			JSONObject result = MessageUtil.send(type, to, FROM, content, formattedReservedTime);
//			String groupId = (String) result.get("group_id");
//			logger.debug("result: ", result.toString());
//			
//			Message record = new Message();
//			record.setMessasgeSeq(message.getMessasgeSeq());
//			record.setGroupId(groupId);
//			record.setState(1);
//			
//			int res = messageMapper.updateByPrimaryKeySelective(record);
//		}
//	}

}
