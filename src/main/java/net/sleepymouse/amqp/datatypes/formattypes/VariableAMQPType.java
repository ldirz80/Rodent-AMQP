/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class VariableAMQPType extends AMQPType
{
	public VariableAMQPType(EncodingType encodingType)
	{
		super(encodingType);
	}
	
}
