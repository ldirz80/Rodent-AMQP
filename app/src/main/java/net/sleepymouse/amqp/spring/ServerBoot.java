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
import net.sleepymouse.amqp.spring.components.log.*;
import net.sleepymouse.amqp.spring.components.types.TypeHandler;
import net.sleepymouse.amqp.spring.services.*;
import net.sleepymouse.amqp.spring.services.network.*;

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
		System.setProperty(SystemConstants.SYSTEM_NAME, LogSystem.SERVER.name());
		System.setProperty(SystemConstants.SUBSYSTEM_NAME, LogSubSystem.SPRING.name());
		//
		ApplicationContext context = SpringApplication.run(ServerBoot.class, args);
		//
		// See what we created
		ILogManager log = context.getBean(LogManager.class);
		log.logRegisteredComponents();
		//
		// Start the system
		IService typeService = context.getBean(TypeHandler.class);
		typeService.start();
		INetworkService networkService = context.getBean(NetworkService.class);
		networkService.start();
		//
		log.info(LoggerFactory.getLogger(ServerBoot.class), "System shutting down", SystemConstants.LogSubSystem.SPRING);
		//
		SpringApplication.exit(context, new ExitCodeGenerator[0]);
	}
}
