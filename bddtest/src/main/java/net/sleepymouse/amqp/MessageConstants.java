/**
 * 
 */
package net.sleepymouse.amqp;

import java.util.*;

/**
 * @author Alan Smithee
 *
 */
public class MessageConstants
{

	/**
	 * Never instantiate
	 */
	private MessageConstants()
	{}

	public enum ProtocolError
	{
		// Version negotation
		PRO_INVALID_AMQP_HEADER(1000), PRO_INVALID_PROTOCOL_ID(1001), PRO_UNSUPPORTED_MAJOR(
				1002), PRO_UNSUPPORTED_MINOR(1003), PRO_UNSUPPORTED_REVISION(1004);

		private long								id;
		private static HashMap<Long, ProtocolError>	encoder	= new HashMap<Long, ProtocolError>();

		static
		{
			EnumSet.allOf(ProtocolError.class).stream().forEach(e -> encoder.put(e.getValue(), e));
		}

		ProtocolError(long id)
		{
			this.id = id;
		}

		public long getValue()
		{
			return id;
		}

		public static ProtocolError getByValue(long id)
		{
			return encoder.get(id);
		}

	};

	public enum ServerEvent
	{
		// Internal issues
		SRV_START(2000), SRV_STOP(2001), SRV_NETWORK(2002), SRV_UNKNOWN(99998), SRV_INTERNAL(99999);

		private long								id;
		private static HashMap<Long, ServerEvent>	encoder	= new HashMap<Long, ServerEvent>();

		static
		{
			EnumSet.allOf(ServerEvent.class).stream().forEach(e -> encoder.put(e.getValue(), e));
		}

		ServerEvent(long id)
		{
			this.id = id;
		}

		public long getValue()
		{
			return id;
		}

		public static ServerEvent getByValue(long id)
		{
			return encoder.get(id);
		}
	};

	public enum Message
	{
		// Internal issues
		MSG_VERSION(3000), MSG_OPEN(3001), MSG_CLOSE(3002), MSG_ACTIVE(3003), MSG_INACTIVE(3004);

		private long							id;
		private static HashMap<Long, Message>	encoder	= new HashMap<Long, Message>();

		static
		{
			EnumSet.allOf(Message.class).stream().forEach(e -> encoder.put(e.getValue(), e));
		}

		Message(long id)
		{
			this.id = id;
		}

		public long getValue()
		{
			return id;
		}

		public static Message getByValue(long id)
		{
			return encoder.get(id);
		}

	};

}
