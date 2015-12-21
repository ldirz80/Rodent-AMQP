/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;
import net.sleepymouse.amqp.utilities.*;

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

	/**
	 * Convert raw bytes into value string
	 */
	@Override
	public String toValue()
	{
		String typeName = this.getEncodingType().getType().getTypeName();
		byte[] raw = getValue();
		switch (typeName)
		{
			case "binary":
				return NumberUtils.dump(raw);
			case "string":
				return TextUtils.toUTF8(raw);
			case "symbol":
				return TextUtils.toASCII(raw);
			default:
				return NumberUtils.dump(raw);
		}
	}
}
