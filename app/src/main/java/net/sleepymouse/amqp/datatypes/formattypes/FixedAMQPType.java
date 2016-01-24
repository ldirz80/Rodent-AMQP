/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

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
