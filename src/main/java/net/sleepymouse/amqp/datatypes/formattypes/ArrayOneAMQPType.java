/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.*;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public class ArrayOneAMQPType extends ArrayAMQPType
{
	public ArrayOneAMQPType(byte[] frameBody, int offset, EncodingType encodingType,
			IPrimitivesManager primitivesManager) throws FrameFormatException
	{
		super(encodingType);
		int pos = offset + 1;
		int size = Byte.toUnsignedInt(frameBody[pos++]); // size in bytes excluding constructor
		int count = Byte.toUnsignedInt(frameBody[pos++]); // number of elements
		//
		long sizeInBytes = size + 1;
		//
		setSize((int) sizeInBytes);
		setCount((int) count);
		//
		// Have to recreate each element so it can be decoded
		if (0 == count)
		{
			return;
		}
		//
		// Decode the first element, as only data changes per element
		AMQPType arrayElement = primitivesManager.decode(frameBody, pos);
		setAMQPType(arrayElement);
		if ((arrayElement instanceof FixedAMQPType) || (arrayElement instanceof VariableAMQPType))
		{
			int dataSize = size - 3;
			setRaw(frameBody, offset + 1, size);
			//
			// Now we have first element so pull out remaining ones
			byte[] elementBuffer = new byte[(int) dataSize + 1];
			byte elementConstructor = frameBody[pos++]; // We are going to re-use this byte for each element
			elementBuffer[0] = elementConstructor;
			elementBuffer = copyInsert(frameBody, elementBuffer, pos, 1, dataSize);
			//
			// Decode each element
			for (int i = 0; i < count; i++)
			{
				AMQPType element = primitivesManager.decode(elementBuffer, 0);
				setElement(i, element);
				elementBuffer = new byte[elementBuffer.length - element.getSize() + 1];
				elementBuffer[0] = elementConstructor;
				pos = pos + element.getSize() - 1;
				elementBuffer = copyInsert(frameBody, elementBuffer, pos, 1, elementBuffer.length - 1);
			}
		}
		else
		{
			throw new FrameFormatException("Unable to decode array element that is not fixed /variable");
		}
	}

	@Override
	public String toValue()
	{
		return "array<" + this.getEncodingType() + ":1>(" + getCount() + ")";
	}

}
