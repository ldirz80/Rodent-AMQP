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
public class VariableFourAMQPType extends VariableAMQPType
{
	public VariableFourAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		int varSize = NumberUtils.getInteger(frameBody, 1);
		//
		setSize(varSize + 5);
		setRaw(frameBody, offset + 5, varSize);
	}

	/**
	 * Convert raw bytes into value string
	 */
	@Override
	public String toValue()
	{
		String typeName = this.getEncodingType().getType().getTypeName();
		byte[] raw = getRaw();
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
