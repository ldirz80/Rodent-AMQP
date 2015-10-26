/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class VariableOneAMQPType extends VariableAMQPType
{
	public VariableOneAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		int varSize = Byte.toUnsignedInt(frameBody[offset + 1]);
		setSize(varSize + 2);
		setValue(frameBody, offset + 2, varSize);
	}
}
