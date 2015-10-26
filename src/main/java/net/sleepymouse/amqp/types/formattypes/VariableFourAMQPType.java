/**
 * 
 */
package net.sleepymouse.amqp.types.formattypes;

import net.sleepymouse.amqp.types.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class VariableFourAMQPType extends VariableAMQPType
{
	public VariableFourAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		int varSize = Byte.toUnsignedInt(frameBody[offset + 1]);
		varSize = varSize << 8 + Byte.toUnsignedInt(frameBody[offset + 2]);
		varSize = varSize << 8 + Byte.toUnsignedInt(frameBody[offset + 3]);
		varSize = varSize << 8 + Byte.toUnsignedInt(frameBody[offset + 4]);
		//
		setSize(varSize + 5);
		setValue(frameBody, offset + 5, varSize);
	}
}
