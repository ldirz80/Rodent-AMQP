/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class FixedFourAMQPType extends FixedAMQPType
{
	public FixedFourAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(5);
		setValue(frameBody, offset+1, 4);
	}
}
