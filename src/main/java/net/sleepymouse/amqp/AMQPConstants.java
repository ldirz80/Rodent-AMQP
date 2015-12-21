/**
 * 
 */
package net.sleepymouse.amqp;

import java.util.*;

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
	public enum FRAME_DESCRIPTOR
	{
		OPEN(0x010), BEGIN(0x11), ATTACH(0x12), FLOW(0x13), TRANSFER(0x14), DISPOSITION(0x15), DETACH(0X16), END(
				0x17), CLOSE(0x18);

		private long									descriptorId;
		private static HashMap<Long, FRAME_DESCRIPTOR>	encoder	= new HashMap<Long, FRAME_DESCRIPTOR>();

		static
		{
			EnumSet.allOf(FRAME_DESCRIPTOR.class).stream().forEach(e -> encoder.put(e.getValue(), e));
		}

		FRAME_DESCRIPTOR(long descriptorId)
		{
			this.descriptorId = descriptorId;
		}

		public long getValue()
		{
			return descriptorId;
		}

		public static FRAME_DESCRIPTOR getByValue(long descriptorId)
		{
			return encoder.get(descriptorId);
		}

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
