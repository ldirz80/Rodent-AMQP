/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class EmptyAMQPType extends FixedAMQPType
{
	public EmptyAMQPType(EncodingType encodingType)
	{
		super(encodingType);
		setSize(1);
		setRaw(new byte[0]);
	}

	/**
	 * Convert raw bytes into value string
	 */
	@Override
	public String toValue()
	{
		String typeName = getEncodingType().getType().getTypeName();
		switch (typeName)
		{
			case "null":
				return "null";
			case "boolean":
			{
				return getEncodingType().getEncoding().getName();
			}
			case "uint":
				return "0";
			case "ulong":
				return "0";
			case "list0":
				return "List<Empty>";
			default:
				return typeName;
		}
	}
}
