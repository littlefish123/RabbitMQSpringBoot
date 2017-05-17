package RabbitMQSpringBoot.RabbitMQSpringBoot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.amqp.core.AmqpTemplate;

public class ReceiveMsgThread implements Runnable{
	String _queue;
	AmqpTemplate _template;
	
	public ReceiveMsgThread(String queue, AmqpTemplate template){
		_queue = queue;
		_template = template;
	}
	
	public void run() {
		String receivedMsg = (String)_template.receiveAndConvert(_queue);
		if(receivedMsg !=  null){
			System.out.print(getCurrentLocalDateTimeStamp() + " - Received  <"+receivedMsg +">");
   		 	System.out.println("");
		}

	}
	public String getCurrentLocalDateTimeStamp(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
