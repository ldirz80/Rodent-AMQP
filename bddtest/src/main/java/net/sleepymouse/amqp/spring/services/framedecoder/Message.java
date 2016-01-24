/**
 * 
 */
package net.sleepymouse.amqp.spring.services.framedecoder;

import net.sleepymouse.amqp.datatypes.formattypes.AMQPType;
import net.sleepymouse.amqp.spring.components.primitives.IPrimitivesManager;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public class Message
{
	private int				size;
	private final AMQPType	header					= null;
	private final AMQPType	deliveryAnnotations		= null;
	private final AMQPType	messageAnnotations		= null;
	private final AMQPType	properties				= null;
	private final AMQPType	applicationProperties	= null;
	private final AMQPType	data					= null;
	private final AMQPType	ampqSequence			= null;
	private final AMQPType	ampqValue				= null;
	private final AMQPType	footer					= null;

	public Message(byte[] messageBody, IPrimitivesManager primitivesManager) throws FrameFormatException
	{
		System.out.println("--- Message (start) --");
		// break up the message
		int pos = 0;
		while (true)
		{
			// grab the constructor byte
			if (messageBody[pos++] != 0x00)
			{
				throw new FrameFormatException("Malformed message body. Message part not begining with 0x00");
			}
			// Get the descriptor
			AMQPType descriptor = primitivesManager.decode(messageBody, pos);
			System.out.println(descriptor);
			String typeName = descriptor.getEncodingType().getType().getTypeName();
			if (!"ulong".equals(typeName))
			{
				throw new FrameFormatException("Malformed performative. Descriptor type is not a ulong");
			}
			pos = pos + descriptor.getSize();
			//
			// Get the contained message section
			AMQPType msg = primitivesManager.decode(messageBody, pos);
			System.out.println(msg);
			pos = pos + msg.getSize();
			if (pos >= messageBody.length)
				break;
		}
		size = messageBody.length;
		System.out.println("--- Message (end) --");
	}

	/**
	 * @return the size
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * @return the header
	 */
	public AMQPType getHeader()
	{
		return header;
	}

	/**
	 * @return the deliveryAnnotations
	 */
	public AMQPType getDeliveryAnnotations()
	{
		return deliveryAnnotations;
	}

	/**
	 * @return the messageAnnotations
	 */
	public AMQPType getMessageAnnotations()
	{
		return messageAnnotations;
	}

	/**
	 * @return the properties
	 */
	public AMQPType getProperties()
	{
		return properties;
	}

	/**
	 * @return the applicationProperties
	 */
	public AMQPType getApplicationProperties()
	{
		return applicationProperties;
	}

	/**
	 * @return the data
	 */
	public AMQPType getData()
	{
		return data;
	}

	/**
	 * @return the ampqSequence
	 */
	public AMQPType getAmpqSequence()
	{
		return ampqSequence;
	}

	/**
	 * @return the ampqValue
	 */
	public AMQPType getAmpqValue()
	{
		return ampqValue;
	}

	/**
	 * @return the footer
	 */
	public AMQPType getFooter()
	{
		return footer;
	}

	@Override
	public String toString()
	{
		StringBuilder response = new StringBuilder(1024);
		//
		//
		return response.toString();
	}

}
