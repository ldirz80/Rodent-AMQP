package net.sleepymouse.amqp.spring.components.primitives;

public class EncodingType
{
	private Type		type;
	private Encoding	encoding;

	public EncodingType(Type type, Encoding encoding)
	{
		this.type = type;
		this.encoding = encoding;
	}

	/**
	 * @return the type
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * @return the encoding
	 */
	public Encoding getEncoding()
	{
		return encoding;
	}

	@Override
	public String toString()
	{
		return type.getTypeName() + "(" + encoding.getName() + ")";
	}
}
