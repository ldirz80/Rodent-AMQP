/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class FixedSixteenAMQPType extends FixedAMQPType
{
	public FixedSixteenAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(17);
		setValue(frameBody, offset+1, 16);
	}
}
