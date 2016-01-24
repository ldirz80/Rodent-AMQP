/**
 * 
 */
package net.sleepymouse.amqp.spring.components.log;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

import javax.inject.Inject;

import org.slf4j.*;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import net.sleepymouse.amqp.MessageConstants.*;
import net.sleepymouse.amqp.SystemConstants;
import net.sleepymouse.amqp.SystemConstants.LogSubSystem;

/**
 * @author Alan Smithee
 *
 *         All logging to go through here
 */
@Component
public class LogManager implements ILogManager
{
	@Inject
	private ApplicationContext		applicationContext;
	@Inject
	private PropertiesFactoryBean	pfb;
	//
	private Logger					logger			= LoggerFactory.getLogger(LogManager.class);

	private final static String		PROTOCOL_PREFIX	= "PRO-";
	private final static String		SERVER_PREFIX	= "SRV-";
	private final static String		UNKNOWN_PREFIX	= "UNK-";
	private final static String		MESSAGE_PREFIX	= "MSG-";

	public LogManager()
	{}

	/**
	 * Some debug stuff ...
	 * 
	 * Dump all registered components - ordered and case insensitive
	 * 
	 * Dump all config variables
	 */
	@Override
	public void logRegisteredComponents()
	{
		// Dump loaded spring beans
		List<String> names = Arrays.asList(applicationContext.getBeanDefinitionNames());
		names.stream().sorted((a, b) -> a.compareToIgnoreCase(b))
				.forEach(e -> info(logger, "Spring Component: " + e, SystemConstants.LogSubSystem.SPRING));
		//
		// Dump properties
		try
		{
			Properties p = pfb.getObject();
			p.forEach((k, v) -> info(logger, k + "-->" + v, SystemConstants.LogSubSystem.SPRING));
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			return;
		}
	}

	/**
	 * Log an info message
	 * 
	 * @param logger
	 *            Logger to register
	 * @param e
	 *            Logger message
	 * @param subsystem
	 *            Subsystem name
	 * @param args
	 *            Optional arguments to be added to message
	 * @return standardised logging message
	 */
	@Override
	public String info(Logger logger, Enum<?> e, LogSubSystem subsystem, Object... args)
	{
		String msg = getMessage(e, args);
		logger.info(msg);
		return msg;
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
	public void info(Logger logger, String msg, LogSubSystem subsystem)
	{
		MDC.put(SystemConstants.SUBSYSTEM_NAME, subsystem.name());
		logger.info(msg);
	}

	/**
	 * Log an error message
	 * 
	 * @param logger
	 *            Logger to register
	 * @param e
	 *            Logger message
	 * @param subsystem
	 *            Subsystem name
	 * @param args
	 *            Optional arguments to be added to message
	 * @return standardised logging message
	 */
	@Override
	public String error(Logger logger, Enum<?> e, LogSubSystem subsystem, Object... args)
	{
		String msg = getMessage(e, args);
		logger.error(msg);
		return msg;
	}

	/**
	 * Log an error message
	 * 
	 * @param logger
	 *            Logger to register
	 * @param msg
	 *            Logger message
	 * @param subsystem
	 *            Subsystem name
	 */
	@Override
	public void error(Logger logger, String msg, LogSubSystem subsystem)
	{
		MDC.put(SystemConstants.SUBSYSTEM_NAME, subsystem.name());
		logger.error(msg);
	}

	/**
	 * Log a warning message
	 * 
	 * @param logger
	 *            Logger to register
	 * @param e
	 *            Logger message
	 * @param subsystem
	 *            Subsystem name
	 * @param args
	 *            Optional arguments to be added to message
	 * @return standardised logging message
	 */
	@Override
	public String warn(Logger logger, Enum<?> e, LogSubSystem subsystem, Object... args)
	{
		String msg = getMessage(e, args);
		logger.warn(msg);
		return msg;
	}

	/**
	 * Log a raning message
	 * 
	 * @param logger
	 *            Logger to register
	 * @param msg
	 *            Logger message
	 * @param subsystem
	 *            Subsystem name
	 */
	@Override
	public void warn(Logger logger, String msg, LogSubSystem subsystem)
	{
		MDC.put(SystemConstants.SUBSYSTEM_NAME, subsystem.name());
		logger.error(msg);
	}

	/**
	 * Generate a standardised log message from the supplied error enum. Intended for use with ProtocolError or
	 * ServerEvent. Can use any enum (But don't, just don't ok ?)
	 * 
	 * @param e
	 *            Enum to generate error message from
	 * @param args
	 *            Message field substitutions
	 * @return Standardised message string
	 */
	private String getMessage(Enum<?> e, Object[] args)
	{
		if (e instanceof ProtocolError)
		{
			ProtocolError protocolError = (ProtocolError) e;
			return getMessage(PROTOCOL_PREFIX, protocolError.name(), protocolError.getValue(), args);
		}
		else if (e instanceof ServerEvent)
		{
			ServerEvent serverError = (ServerEvent) e;
			return getMessage(SERVER_PREFIX, serverError.name(), serverError.getValue(), args);
		}
		else if (e instanceof Message)
		{
			Message message = (Message) e;
			return getMessage(MESSAGE_PREFIX, message.name(), message.getValue(), args);
		}
		else
		{
			return UNKNOWN_PREFIX + String.format("%05d", 0) + ":" + e.name() + ":" + "Unknown";
		}
	}

	/**
	 * 
	 * @param prefix
	 *            Error message prefix text
	 * @param name
	 *            Enum name (used as key for I18N text lookup)
	 * @param value
	 *            Error code
	 * @param args
	 *            Message field substitutions
	 * @return Standardised message string
	 */
	private String getMessage(String prefix, String name, long value, Object[] args)
	{
		try
		{
			String i18nText = pfb.getObject().getProperty(name, name);
			i18nText = MessageFormat.format(i18nText, args);
			String errorCode = prefix + String.format("%05d", value);
			return errorCode + ":" + name + ":" + i18nText;
		}
		catch (Exception e)
		{
			String errorCode = prefix + String.format("%05d", ServerEvent.SRV_INTERNAL.getValue());
			return SERVER_PREFIX + errorCode + ":" + name + ":" + e.getMessage();
		}
	}

}
