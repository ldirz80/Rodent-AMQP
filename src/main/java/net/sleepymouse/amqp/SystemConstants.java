package net.sleepymouse.amqp;

public class SystemConstants
{
	//
	// Logging - standardise field names for ELMK processing of logs
	public final static String	SYSTEM_NAME		= "SYSTEM";
	public final static String	SUBSYSTEM_NAME	= "SUBSYSTEM";

	public final static String	EOL				= System.getProperty("line.separator");

	//
	public static enum LogSystem
	{
		SERVER
	}

	//
	public static enum LogSubSystem
	{
		SPRING, PRIMITIVES, NETWORK
	}

}
