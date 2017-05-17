package RabbitMQSpringBoot.RabbitMQSpringBoot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

import com.rabbitmq.client.Channel;

public class MyConsumer  implements ChannelAwareMessageListener{
	 public void onMessage(Message message, Channel channel) throws Exception {  
	    	System.out.println("MyConsumer:OnMessage");  
	        MessageProperties messageProperties = message.getMessageProperties();  
	        String messageContent = (String) new SimpleMessageConverter().fromMessage(message);  
	        System.out.println("r[" + message + "]");  
	          
	         channel.basicAck(messageProperties.getDeliveryTag(), false);  
	          
	          
	        System.out.println("yorkyorkdoword="+messageContent);
	        System.out.println(messageProperties.getDeliveryTag());  
	  

	        channel.basicNack(messageProperties.getDeliveryTag(), false,false);
	  
	        System.out.println("r[done]");  
	    }  
}
