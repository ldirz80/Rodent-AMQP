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
public class FixedFourAMQPType extends FixedAMQPType
{
	public FixedFourAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(5);
		setValue(frameBody, offset + 1, 4);
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
			case "uint":
				return NumberUtils.toUnsignedInteger(raw);
			case "int":
				return NumberUtils.toSignedInteger(raw);
			case "float":
				return NumberUtils.toFloat754(raw);
			case "decimal32":
				return NumberUtils.toDecimal_32_754(raw);
			case "char":
				return TextUtils.toUTF32BE(raw);
			default:
				return NumberUtils.dump(raw);
		}
	}
}
