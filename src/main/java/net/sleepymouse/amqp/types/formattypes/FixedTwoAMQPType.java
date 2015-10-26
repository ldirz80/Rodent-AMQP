/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class FixedTwoAMQPType extends FixedAMQPType
{
	public FixedTwoAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(3);
		setValue(frameBody, offset + 1, 2);
	}
}
