/**
 * 
 */
package net.sleepymouse.amqp.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import net.sleepymouse.amqp.SystemConstants;
import net.sleepymouse.amqp.SystemConstants.*;
import net.sleepymouse.amqp.spring.managers.oogManager.*;

/**
 * @author richard.espley
 *
 */

@Configuration
@EnableAutoConfiguration()
@ComponentScan({ "net.sleepymouse.amqp" })
public class ServerBoot
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Default system / subsystem names for logging
		System.setProperty(SystemConstants.SYSTEM_NAME, LOG_SYSTEM.SERVER.name());
		System.setProperty(SystemConstants.SUBSYSTEM_NAME, LOG_SUB_SYSTEM.SPRING.name());
		//
		ApplicationContext applicationContext = SpringApplication.run(ServerBoot.class, args);
		//
		// See what we created
		ILogManager log = applicationContext.getBean(LogManager.class);
		log.logRegisteredComponents();
	}
}
