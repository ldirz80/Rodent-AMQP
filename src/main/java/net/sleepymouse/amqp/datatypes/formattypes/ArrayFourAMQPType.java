/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.*;
import net.sleepymouse.amqp.utilities.*;

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
		int pos = offset + 1;
		long size = NumberUtils.getLong(frameBody, pos, 4); // size in bytes excluding constructor
		pos = pos + 4;
		long count = NumberUtils.getLong(frameBody, pos, 4); // number of elements
		pos = pos + 4;
		//
		long sizeInBytes = size + 1;
		if (sizeInBytes > Integer.MAX_VALUE)
		{
			throw new FrameFormatException("Maximum array size (" + Integer.MAX_VALUE + ") exceeded");
		}
		//
		if (count > Integer.MAX_VALUE)
		{
			throw new FrameFormatException("Maximum array element count (" + Integer.MAX_VALUE + ") exceeded");
		}
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
			long dataSize = size - 9;
			if (dataSize > Integer.MAX_VALUE)
			{
				throw new FrameFormatException("Maximum array data size (" + Integer.MAX_VALUE + ") exceeded");
			}
			setValue(frameBody, offset + 1, (int) size);
			//
			// Now we have first element so pull out remaining ones
			byte[] elementBuffer = new byte[(int) dataSize + 1];
			byte elementConstructor = frameBody[pos++]; // We are going to re-use this byte for each element
			elementBuffer[0] = elementConstructor;
			elementBuffer = copyInsert(frameBody, elementBuffer, pos, 1, (int) dataSize);
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
		return "array<" + this.getEncodingType() + ":4>(" + getCount() + ")";
	}

}
