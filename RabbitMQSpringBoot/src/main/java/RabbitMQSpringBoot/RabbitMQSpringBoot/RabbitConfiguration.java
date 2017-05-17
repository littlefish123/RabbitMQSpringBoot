package RabbitMQSpringBoot.RabbitMQSpringBoot;

import org.apache.tomcat.util.bcel.classfile.ConstantInteger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
    	CachingConnectionFactory connectionFactory =  new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    	return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /*
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }*/
    
    @Bean
    public AmqpTemplate amqpTemplate(){
    	return new RabbitTemplate(connectionFactory());
    }
    

    @Bean
    public Queue myQueue() {
    	Queue queue = new Queue("myQueue");
       return queue;
    }
    
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(){
    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    	container.setConnectionFactory(connectionFactory());
    	container.setQueueNames("myQueue");
    	container.setMessageListener(consumer());
    	container.setPrefetchCount(1);
    	container.setAcknowledgeMode(AcknowledgeMode.NONE);
    	return container;
    }
    
    @Bean
    public ChannelAwareMessageListener consumer(){
    	return new MyConsumer();
    }
    
}
