/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import java.util.UUID;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;
import net.sleepymouse.amqp.utilities.NumberUtils;

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
		setValue(frameBody, offset + 1, 16);
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
			case "ulong":
				return NumberUtils.toUnsignedInteger(raw);
			case "long":
				return NumberUtils.toSignedInteger(raw);
			case "double":
				return NumberUtils.toDouble754(raw);
			case "decimal128":
				return NumberUtils.toDecimal_128_754(raw);
			case "uuid":
			{
				long mostSigBits = NumberUtils.getLong(raw, 0, 8);
				long leastSigBits = NumberUtils.getLong(raw, 8, 8);
				UUID uuid = new UUID(mostSigBits, leastSigBits);
				return uuid.toString();
			}
			default:
				return NumberUtils.dump(raw);
		}
	}
}
