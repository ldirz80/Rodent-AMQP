/**
 * 
 */
package net.sleepymouse.amqp.spring.services.framedecoder;

import static net.sleepymouse.amqp.SystemConstants.EOL;

import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public class Frame
{
	private int				size;
	private int				doff;
	private int				type;
	private int				channel;
	private Performative	performative;
	private Message			message;
	private byte[]			frameBody;
	private byte[]			extendedHeader;

	//

	public Frame(int size, int doff, int type, int channel, byte[] ignored, byte[] frameBody, Performative performative,
			Message message) throws FrameFormatException
	{
		this.size = size;
		this.doff = doff;
		this.type = type;
		this.channel = channel;
		this.performative = performative;
		this.frameBody = frameBody;
		this.extendedHeader = ignored;
		this.message = message;
	}

	/**
	 * @return the size
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * @return the doff
	 */
	public int getDoff()
	{
		return doff;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @return the channel
	 */
	public int getChannel()
	{
		return channel;
	}

	/**
	 * @return the message
	 */
	public Message getMessage()
	{
		return message;
	}

	/**
	 * @return the performative
	 */
	public Performative getPerformative()
	{
		return performative;
	}

	@Override
	public String toString()
	{
		String response = "-- FRAME_DESCRIPTOR --" + EOL + //
				"Frame size: " + size + " bytes" + EOL + //
				"DOFF: " + doff + EOL + //
				"Type: " + type + EOL + //
				"Extended header: " + extendedHeader.length + " bytes" + EOL + //
				"Message: " + message.getSize() + " bytes" + EOL + //
				performative.toString();
		//
		return response;
	}

}
