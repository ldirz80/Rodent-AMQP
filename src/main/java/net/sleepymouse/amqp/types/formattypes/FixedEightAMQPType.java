/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class FixedEightAMQPType extends FixedAMQPType
{
	public FixedEightAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(9);
		setValue(frameBody, offset + 1, 8);
	}
}
