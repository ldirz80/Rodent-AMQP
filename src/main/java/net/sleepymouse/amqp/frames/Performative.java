/**
 * 
 */
package net.sleepymouse.amqp.frames;

import net.sleepymouse.amqp.types.formattypes.AMQPType;
import net.sleepymouse.amqp.types.primitives.IPrimitivesManager;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public class Performative
{
	private final int		size;
	private final AMQPType	performativeFields;
	private final AMQPType	descriptorType;
	private final long		domainId;
	private final long		descriptorId;

	public Performative(byte[] frameBody, IPrimitivesManager primitivesManager) throws FrameFormatException
	{
		// grab the constructor byte
		if (frameBody[0] != 0x00)
		{
			throw new FrameFormatException("Malformed performative. Performative not begining with 0x00");
		}
		//
		// Should have the descriptor next, e.g. <descriptor name="amqp:open:list" code="0x00000000:0x00000010"/>
		// Represented as a 64 bit ulong

		descriptorType = primitivesManager.decode(frameBody, 1);
		String typeName = descriptorType.getEncodingType().getType().getName();
		if (!"ulong".equals(typeName))
		{
			throw new FrameFormatException("Malformed performative. Descriptor type is not a ulong");
		}
		//
		// Get the domain and descriptor Id's
		byte[] raw = descriptorType.getValue();
		if (0 == raw.length)
		{
			domainId = 0;
			descriptorId = 0;
		}
		else
		{
			long temp = 0;
			for (int i = 0; i < raw.length; i++)
			{
				temp = temp << 8 | Byte.toUnsignedLong(raw[i]);
			}
			domainId = temp & 0xFFFF_FFFF_0000_0000l;
			descriptorId = temp & 0x00000000_FFFFFFFFl;
		}
		//
		// Load list of performative fields
		performativeFields = primitivesManager.decode(frameBody, descriptorType.getSize() + 1);
		//
		size = descriptorType.getSize() + performativeFields.getSize();
	}

	/**
	 * @return the size
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * Get the fields that make up the performative. Will be a list
	 * 
	 * @return AMQP type which holds the performative
	 */
	public AMQPType getPerformativeFields()
	{
		return performativeFields;
	}

	/**
	 * Get the performative domain id
	 * 
	 * @return Domain id
	 */
	public long getDomainId()
	{
		return domainId;
	}

	/**
	 * Get the performative descriptor id
	 * 
	 * @return Descriptor id
	 */
	public long getDescriptorId()
	{
		return descriptorId;
	}
}
