package net.sleepymouse.amqp;

public class SystemConstants
{
	//
	// Logging - standardise field names for ELMK processing of logs
	public final static String	SYSTEM_NAME		= "SYSTEM";
	public final static String	SUBSYSTEM_NAME	= "SUBSYSTEM";

	// 
	public static enum LOG_SYSTEM
	{
		SERVER
	}
	
	// 
	public static enum LOG_SUB_SYSTEM
	{
		SPRING
	}

}
