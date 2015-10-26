/**
 * 
 */
package net.sleepymouse.amqp;

/**
 * @author Alan Smithee
 *
 */
public class AMQPConstants
{
	public final static int	AMQP_MAJOR			= 1;
	public final static int	AMQP_MINOR			= 0;
	public final static int	AMQP_REVISION		= 0;
	//
	public final static int	PORT				= 5000;
	// //
	public final static int	AMQP_TYPE			= 0x00;
	public final static int	SASL_TYPE			= 0x01;
	//
	public final static int	MIN_MAX_FRAME_SIZE	= 512;

	//
	public static enum FRAME
	{
		OPEN, BEGIN, ATTACH, FLOW, TRANSFER, DISPOSITION, DETACH, END, CLOSE, NULL
	};

	public static enum CONNECTION_STATE
	{
		START, HDR_RCVD, HDR_SENT, HDR_EXCH, OPEN_PIPE, OC_PIPE, OPEN_RCVD, OPEN_SENT, CLOSE_PIPE, OPENED, CLOSE_RCVD, CLOSE_SENT, DISCARDING, END
	};

	public static enum SESSION_STATE
	{
		UNMAPPED, BEGIN_SENT, BEGIN, RCVD, MAPPED, END_SENT, END_RCVDM, DISCARDING
	};

	private AMQPConstants()
	{}

}
