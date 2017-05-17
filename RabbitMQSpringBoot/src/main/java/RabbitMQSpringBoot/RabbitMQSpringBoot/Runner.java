package RabbitMQSpringBoot.RabbitMQSpringBoot;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;


@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;
    private final ConfigurableApplicationContext context;
    
    public Runner(RabbitTemplate rabbitTemplate,
            ConfigurableApplicationContext context) {
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }
    public void run(String... args) throws Exception {
        for(int i = 0; true;){
        	//receiver.getLatch().await(5000, TimeUnit.MILLISECONDS);
            //System.out.print("");
            

    		AmqpTemplate template = App.rabbitContext.getBean(AmqpTemplate.class);
    		AmqpAdmin admin = App.rabbitContext.getBean(AmqpAdmin.class);
    		
    		String route = "mylogs4";
    		String receivedMsg = (String)template.receiveAndConvert(route);
    		if(receivedMsg !=  null){

    			System.out.print(getCurrentLocalDateTimeStamp() + " - Received  <"+receivedMsg +">");
       		 	System.out.println("");
    		}
			//Runnable r = new ReceiveMsgThread(route, template);
			//new Thread(r).start();

    		
    		/*
    		for(int j =0; j<10;j++){
    			Runnable r = new ReceiveMsgThread(route, template);
    			new Thread(r).start();
    		}*/

    		
        }
	}
    
	public String getCurrentLocalDateTimeStamp(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}

}
