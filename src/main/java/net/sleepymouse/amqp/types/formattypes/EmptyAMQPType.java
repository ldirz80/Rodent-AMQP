/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class EmptyAMQPType extends FixedAMQPType
{
	public EmptyAMQPType(EncodingType encodingType)
	{
		super(encodingType);
		setSize(1);
	}
}
