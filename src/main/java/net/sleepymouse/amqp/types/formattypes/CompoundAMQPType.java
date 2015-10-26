/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class CompoundAMQPType extends AMQPType
{
	public CompoundAMQPType(EncodingType encodingType)
	{
		super(encodingType);
	}

}
