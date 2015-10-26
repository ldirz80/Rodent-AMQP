/**
 * 
 */
package net.sleepymouse.amqp.types;

import net.sleepymouse.amqp.types.formattypes.AMQPType;
import net.sleepymouse.amqp.types.primitives.PrimitivesManager;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public class Constructor
{
	private Constructor	constructor;
	private Constructor	descriptor;
	private boolean		formatCode;
	private AMQPType	amqpType;
	private int			size;

	public Constructor(byte[] frameBody, int offset, PrimitivesManager primitivesManager) throws FrameFormatException
	{
		if (0x0 == frameBody[offset])
		{
			formatCode = false;
			descriptor = new Constructor(frameBody, offset + 1, primitivesManager);
			constructor = new Constructor(frameBody, offset + 1 + descriptor.getSize(), primitivesManager);
			size = descriptor.getSize() + constructor.getSize() + 1;
		}
		else
		{
			formatCode = true;
			amqpType = primitivesManager.decode(frameBody, offset);
			size = amqpType.getSize();
		}
	}

	/**
	 * @return the constructor
	 */
	public Constructor getConstructor()
	{
		return constructor;
	}

	/**
	 * @return the descriptor
	 */
	public Constructor getDescriptor()
	{
		return descriptor;
	}

	/**
	 * @return the formatCode
	 */
	public boolean isFormatCode()
	{
		return formatCode;
	}

	/**
	 * @return the amqpType
	 */
	public AMQPType getAmqpType()
	{
		return amqpType;
	}

	public int getSize()
	{
		return size;
	}
}
