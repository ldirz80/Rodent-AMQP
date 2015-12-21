/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import static net.sleepymouse.amqp.SystemConstants.EOL;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class ArrayAMQPType extends AMQPType
{
	private int			count;
	private AMQPType	amqpType;
	private AMQPType[]	elements;

	public ArrayAMQPType(EncodingType encodingType)
	{
		super(encodingType);
	}

	void setCount(int count)
	{
		this.count = count;
		elements = new AMQPType[count];
	}

	public int getCount()
	{
		return count;
	}

	void setAMQPType(AMQPType amqpType)
	{
		this.amqpType = amqpType;
	}

	public AMQPType getAMQPCount()
	{
		return amqpType;
	}

	AMQPType getElement(int i)
	{
		return elements[i];
	}

	void setElement(int i, AMQPType element)
	{
		elements[i] = element;
	}

	byte[] copyInsert(byte[] source, byte[] target, int from, int to, int copySize)
	{
		for (int i = 0; i < copySize; i++)
		{
			target[to++] = source[from++];
		}
		return target;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(512);
		sb.append(getEncodingType() + "[" + elements.length + "]").append(EOL);
		for (int i = 0; i < elements.length; i++)
		{
			sb.append('\t').append(i).append(' ').append(elements[i].toString()).append(EOL);
		}
		return sb.toString();
	}
}
