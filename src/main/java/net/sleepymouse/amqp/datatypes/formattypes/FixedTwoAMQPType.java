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
public class FixedTwoAMQPType extends FixedAMQPType
{
	public FixedTwoAMQPType(byte[] frameBody, int offset, EncodingType encodingType)
	{
		super(encodingType);
		setSize(3);
		setValue(frameBody, offset + 1, 2);
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
			case "ushort":
				return NumberUtils.toUnsignedInteger(raw);
			case "short":
				return NumberUtils.toSignedInteger(raw);
			default:
				return NumberUtils.dump(raw);
		}
	}
}
