/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class FixedAMQPType extends AMQPType
{
	public FixedAMQPType(EncodingType encodingType)
	{
		super(encodingType);
	}
}
