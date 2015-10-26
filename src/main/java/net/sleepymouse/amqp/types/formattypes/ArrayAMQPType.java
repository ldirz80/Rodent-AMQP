/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class ArrayAMQPType extends AMQPType
{
	private int			count;
	private AMQPType	amqpType;

	public ArrayAMQPType(EncodingType encodingType)
	{
		super(encodingType);
	}

	void setCount(int count)
	{
		this.count = count;
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
}
