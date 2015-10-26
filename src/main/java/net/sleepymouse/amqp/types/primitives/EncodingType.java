package net.sleepymouse.amqp.types.primitives;

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
}
