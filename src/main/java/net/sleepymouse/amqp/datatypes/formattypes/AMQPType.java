/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import java.util.Arrays;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

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

	void setValue(byte[] value)
	{
		this.value = value;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
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

	public abstract String toValue();

	@Override
	public String toString()
	{
		return encodingType + ":" + size + ":" + toValue();
	}

}
