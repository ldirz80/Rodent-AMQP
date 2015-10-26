/**
 * 
 */
package net.sleepymouse.amqp.frames;

import java.util.Arrays;

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
	private byte[]			frameBody;

	public Frame(int size, int doff, int type, int channel, byte[] ignored, byte[] frameBody, Performative performative)
			throws FrameFormatException
	{
		this.size = size;
		this.doff = doff;
		this.type = type;
		this.channel = channel;
		this.performative = performative;
		this.frameBody = frameBody;
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
	 * @return the payload
	 */
	public byte[] getPayload()
	{
		int payloadSize = frameBody.length - performative.getSize();
		if (0 == payloadSize)
		{
			return new byte[0];
		}
		else
		{
			return Arrays.copyOfRange(frameBody, performative.getSize(), frameBody.length);
		}
	}

	/**
	 * @return the performative
	 */
	public Performative getPerformative()
	{
		return performative;
	}

}
