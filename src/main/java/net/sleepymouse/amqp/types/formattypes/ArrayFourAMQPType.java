/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.*;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public class ArrayFourAMQPType extends ArrayAMQPType
{
	public ArrayFourAMQPType(byte[] frameBody, int offset, EncodingType encodingType,
			IPrimitivesManager primitivesManager) throws FrameFormatException
	{
		super(encodingType);
		int arraySize = Byte.toUnsignedInt(frameBody[offset + 1]);
		int arrayCount = Byte.toUnsignedInt(frameBody[offset + 2]);
		//
		setSize(arraySize + 1);
		setCount(arrayCount);
		//
		// Have to recreate each element so it can be decoded
		if (0 == arraySize)
		{
			return;
		}
		//
		// Decode the first element, as only data changes per element

		AMQPType arrayElement = primitivesManager.decode(frameBody, offset + 3);
		setAMQPType(arrayElement);
		if ((arrayElement instanceof FixedAMQPType) || (arrayElement instanceof VariableAMQPType))
		{
			int elementSize = arrayElement.getDataSize();
			int totalDataSize = elementSize * arrayCount;
			int elementConstructorSize = arrayElement.getSize() - arrayElement.getDataSize();
			int dataStart = offset + 3 + elementConstructorSize;
			this.setValue(frameBody, dataStart, dataStart + totalDataSize); // copy data elements only
		}
		else
		{
			throw new FrameFormatException("Unable to decode array element that is nof fixed / variable");
		}
	}
}
