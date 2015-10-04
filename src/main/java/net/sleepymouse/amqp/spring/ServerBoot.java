/**
 * 
 */
package net.sleepymouse.amqp.spring;

import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import net.sleepymouse.amqp.SystemConstants;
import net.sleepymouse.amqp.SystemConstants.*;
import net.sleepymouse.amqp.spring.managers.logManager.*;
import net.sleepymouse.amqp.spring.services.*;

/**
 * @author Alan Smithee
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
		ApplicationContext context = SpringApplication.run(ServerBoot.class, args);
		//
		// See what we created
		ILogManager log = context.getBean(LogManager.class);
		log.logRegisteredComponents();
		//
		// Start the system
		IService typeService = context.getBean(TypeService.class);
		typeService.start();
		typeService.stop();
		//
		log.info(LoggerFactory.getLogger(ServerBoot.class), "System shutting down", SystemConstants.LOG_SUB_SYSTEM.SPRING.name());
		//
		SpringApplication.exit(context, new ExitCodeGenerator[0]);
	}
}
