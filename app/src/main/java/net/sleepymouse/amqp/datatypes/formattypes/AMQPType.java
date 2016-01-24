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
	private int				size;							// Total size in bytes of encoding including format code
	private byte[]			value			= new byte[0];
	private EncodingType	encodingType	= null;

	public AMQPType(EncodingType encodingType)
	{
		this.encodingType = encodingType;
	}

	/**
	 * Raw bytes representing this type - Does not include the format code
	 * 
	 * @return Raw representation
	 */
	public byte[] getRaw()
	{
		return value;
	}

	/**
	 * Set the bytes representing this type - Does not include the format code
	 * 
	 * @return Raw representation
	 * @param frameBody
	 *            The complete frame
	 * @param offset
	 *            Place to start copy
	 * @param dataSize
	 *            Amount to copy
	 */
	void setRaw(byte[] frameBody, int offset, int dataSize)
	{
		setRaw(Arrays.copyOfRange(frameBody, offset, offset + dataSize));
	}

	/**
	 * Set the bytes representing this type - Does not include the format code
	 * 
	 * @param value
	 *            Raw representation
	 */
	void setRaw(byte[] value)
	{
		this.value = value;
	}

	/**
	 * Get the total size of the type including format code
	 * 
	 * @return Size in bytes
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * Set the total size of the type including format code
	 * 
	 * @param size
	 *            Size in bytes
	 */
	public void setSize(int size)
	{
		this.size = size;
	}

	/**
	 * Get the size of the raw data representation
	 * 
	 * @return Size in bytes
	 */
	public int getRawSize()
	{
		return value.length;
	}

	/**
	 * Get the encoding type data structure for the type stored. This comprises the Type data and the associated
	 * Encoding. For example:
	 * 
	 * <type name="ulong" class="primitive">
	 * 
	 * 
	 * <encoding code="0x80" category="fixed" width="8" label= "64-bit unsigned integer in network byte order" />
	 * 
	 * @return the encodingType
	 */
	public EncodingType getEncodingType()
	{
		return encodingType;
	}

	/**
	 * Get a 'human readable' representation of the type
	 * 
	 * @return Readable string
	 */
	public abstract String toValue();

	@Override
	public String toString()
	{
		return encodingType + ":" + size + ":" + toValue();
	}

}
