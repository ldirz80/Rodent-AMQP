package net.sleepymouse.amqp.spring.components.log;

import org.slf4j.Logger;

import net.sleepymouse.amqp.SystemConstants.LogSubSystem;

public interface ILogManager
{
	/**
	 * Dump all registered components - ordered and case insensitive
	 */
	public void logRegisteredComponents();

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
	public String info(Logger logger, Enum<?> e, LogSubSystem subsystem, Object... args);

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
	public void info(Logger logger, String msg, LogSubSystem subsystem);

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
	public String error(Logger logger, Enum<?> e, LogSubSystem subsystem, Object... args);

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
	public void error(Logger logger, String msg, LogSubSystem subsystem);

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
	public String warn(Logger logger, Enum<?> e, LogSubSystem subsystem, Object... args);

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
	public void warn(Logger logger, String msg, LogSubSystem subsystem);
}
