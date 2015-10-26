/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class FixedOneAMQPType extends FixedAMQPType
{
	public FixedOneAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(2);
		setValue(frameBody, offset+1, 1);
	}
}
