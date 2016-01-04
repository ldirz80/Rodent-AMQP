/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;
import net.sleepymouse.amqp.utilities.NumberUtils;

/**
 * @author Alan Smithee
 *
 */
public class FixedEightAMQPType extends FixedAMQPType
{
	public FixedEightAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(9);
		setRaw(frameBody, offset + 1, 8);
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
			case "ulong":
				return NumberUtils.toUnsignedInteger(raw);
			case "long":
				return NumberUtils.toSignedInteger(raw);
			case "timestamp":
				return NumberUtils.toTimestamp(raw);
			case "double":
				return NumberUtils.toDouble754(raw);
			case "decimal64":
				return NumberUtils.toDecimal_64_754(raw);
			default:
				return NumberUtils.dump(raw);
		}
	}
}
