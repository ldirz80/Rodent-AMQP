/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import java.util.Arrays;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class AMQPType
{
	private int				size;			// Total size in bytes of encoding including format code
	private byte[]			value;
	private EncodingType	encodingType;

	public AMQPType(EncodingType encodingType)
	{
		this.encodingType = encodingType;
	}

	public byte[] getValue()
	{
		return value;
	}

	void setValue(byte[] frameBody, int offset, int dataSize)
	{
		value = Arrays.copyOfRange(frameBody, offset, offset + dataSize);
	}

	public int getSize()
	{
		return size;
	}

	void setSize(int size)
	{
		this.size = size;
	}

	public int getDataSize()
	{
		return value.length;
	}

	/**
	 * @return the encodingType
	 */
	public EncodingType getEncodingType()
	{
		return encodingType;
	}

}
