package RabbitMQSpringBoot.RabbitMQSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
	public static ApplicationContext rabbitContext = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
	 
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(App.class, args);
    }
    
}
