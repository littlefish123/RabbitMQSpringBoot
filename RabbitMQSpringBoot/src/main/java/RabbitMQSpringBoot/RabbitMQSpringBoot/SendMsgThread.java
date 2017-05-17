package RabbitMQSpringBoot.RabbitMQSpringBoot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.amqp.core.AmqpTemplate;

public class SendMsgThread implements Runnable{
	String _message;
	String _queue;
	AmqpTemplate _template;
	
	public SendMsgThread(String message, String queue, AmqpTemplate template){
		_message = message;
		_queue = queue;
		_template = template;
	}
	
	public void run() {
		for(int i =0; i< 100000;i++){
			String sendMsg = getCurrentLocalDateTimeStamp() +"|"+_message;
			_template.convertAndSend(_queue,_queue, sendMsg);
		}
		
	}
	public String getCurrentLocalDateTimeStamp(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}

}
