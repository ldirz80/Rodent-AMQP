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
public class FixedOneAMQPType extends FixedAMQPType
{
	public FixedOneAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(2);
		setRaw(frameBody, offset + 1, 1);
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
			case "ubyte":
			case "ulong":
			case "uint":
				return NumberUtils.toUnsignedInteger(raw);
			case "byte":
			case "int":
			case "long":
				return NumberUtils.toSignedInteger(raw);
			case "boolean":
				return (0 == raw[1]) ? "false" : "true";
			default:
				return NumberUtils.dump(raw);
		}
	}
}
