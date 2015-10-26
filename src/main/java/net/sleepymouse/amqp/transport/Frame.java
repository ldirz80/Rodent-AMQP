package net.sleepymouse.amqp.transport;

/**
 * @author Alan Smithee
 *
 */
public class Frame
{
	private int	channelNumber;
	private int	frameSequence;

	public Frame(int channelNumber, int frameSequence)
	{
		this.channelNumber = channelNumber;
		this.frameSequence = frameSequence;
	}

	/**
	 * @return the channelNumber
	 */
	public int getChannelNumber()
	{
		return channelNumber;
	}

	/**
	 * @return the frameSequence
	 */
	public int getFrameSequence()
	{
		return frameSequence;
	}
}
