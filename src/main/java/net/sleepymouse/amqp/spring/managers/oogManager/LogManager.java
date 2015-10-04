/**
 * 
 */
package net.sleepymouse.amqp.spring.managers.oogManager;

import java.util.*;

import javax.inject.Inject;

import org.slf4j.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import net.sleepymouse.amqp.SystemConstants;

/**
 * @author richard.espley
 *
 */
@Component
public class LogManager implements ILogManager
{
	protected Logger				logger;
	protected ApplicationContext	applicationContext;

	/**
	 * Dump all registered components - ordered and case insensitive
	 */
	@Override
	public void logRegisteredComponents()
	{
		List<String> names = Arrays.asList(applicationContext.getBeanDefinitionNames());

		names.stream().sorted((a, b) -> a.compareToIgnoreCase(b))
				.forEach(e -> info(logger, "Spring Component: " + e, SystemConstants.LOG_SUB_SYSTEM.SPRING.name()));
	}

	/**
	 * Log an info message
	 * 
	 * @param logger
	 *            Logger to register
	 * @param msg
	 *            Logger message
	 * @param subsystem
	 *            Subsystem name
	 */
	@Override
	public void info(Logger logger, String msg, String subsystem)
	{
		MDC.put(SystemConstants.SUBSYSTEM_NAME, subsystem);
		logger.info(msg);
	}

	@Inject
	public LogManager(ApplicationContext ctx)
	{
		this.logger = LoggerFactory.getLogger(LogManager.class);
		this.applicationContext = ctx;
	}

}
