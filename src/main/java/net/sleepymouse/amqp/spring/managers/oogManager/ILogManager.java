package net.sleepymouse.amqp.spring.managers.oogManager;

import org.slf4j.Logger;

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
	 * @param msg
	 *            Logger message
	 * @param subsystem
	 *            Subsystem name
	 */
	public void info(Logger logger, String msg, String subsystem);
}
