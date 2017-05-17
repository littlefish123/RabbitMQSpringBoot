package RabbitMQSpringBoot.RabbitMQSpringBoot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	@RequestMapping("/sendMessage")
	public String home4(@RequestParam(value="queue",defaultValue="myQueue") String queueName, 
			@RequestParam(value="message",defaultValue="test") String message, HttpServletRequest request){

		AmqpTemplate template = App.rabbitContext.getBean(AmqpTemplate.class);
		
		AmqpAdmin admin = App.rabbitContext.getBean(AmqpAdmin.class);
		String sendMsg = getCurrentLocalDateTimeStamp() +"|"+message;
		String route = queueName;
		Queue queue = new Queue(route,true);
		Exchange exchange = new TopicExchange("RabbitMQSpringBoot");
		admin.declareQueue(queue);
		admin.declareExchange(exchange);
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(route).noargs();
		admin.declareBinding(binding);

		template.convertAndSend(queueName,queueName, sendMsg);

		String msg = sendMsg;
		return msg;
	}
	
	@RequestMapping("/sendMilMessage")
	public String sendMilMessage(@RequestParam(value="queue",defaultValue="myQueue") String queueName, 
			@RequestParam(value="message",defaultValue="test") String message, HttpServletRequest request){

		AmqpTemplate template = App.rabbitContext.getBean(AmqpTemplate.class);
		
		AmqpAdmin admin = App.rabbitContext.getBean(AmqpAdmin.class);

		String route = queueName;
		Queue queue = new Queue(route,true);
		Exchange exchange = new TopicExchange("RabbitMQSpringBoot");
		admin.declareQueue(queue);
		admin.declareExchange(exchange);
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(route).noargs();
		admin.declareBinding(binding);

		for(int i =0; i<10;i++){
			Runnable r = new SendMsgThread(message, queueName, template);
			new Thread(r).start();
		}
		return "done";
	}
	
	public String getCurrentLocalDateTimeStamp(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
	
	@RequestMapping("/showMQdepth")
	
	public String showMQdepth(@RequestParam(value="queue",defaultValue="myQueue") String message, HttpServletRequest request){
		AmqpAdmin admin = App.rabbitContext.getBean(AmqpAdmin.class);
		return 	admin.getQueueProperties(message).toString();
	}
}
