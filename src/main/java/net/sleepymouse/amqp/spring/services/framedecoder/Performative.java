/**
 * 
 */
package net.sleepymouse.amqp.spring.services.framedecoder;

import static net.sleepymouse.amqp.SystemConstants.EOL;

import net.sleepymouse.amqp.AMQPConstants.FRAME_DESCRIPTOR;
import net.sleepymouse.amqp.datatypes.formattypes.AMQPType;
import net.sleepymouse.amqp.spring.components.primitives.IPrimitivesManager;
import net.sleepymouse.amqp.utilities.*;

/**
 * @author Alan Smithee
 *
 */
public class Performative
{
	private final int		size;
	private final AMQPType	performativeFields;
	private final AMQPType	descriptor;
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

		descriptor = primitivesManager.decode(frameBody, 1);
		String typeName = descriptor.getEncodingType().getType().getTypeName();
		if (!"ulong".equals(typeName))
		{
			throw new FrameFormatException("Malformed performative. Descriptor type is not a ulong");
		}
		//
		// Get the domain and descriptor Id's (32 bit parts of 64 bit ulong)
		byte[] raw = descriptor.getRaw();
		if (0 == raw.length)
		{
			domainId = 0;
			descriptorId = 0;
		}
		else
		{
			long temp = NumberUtils.getLong(raw);
			domainId = temp & 0xFFFF_FFFF_0000_0000l;
			descriptorId = temp & 0x00000000_FFFFFFFFl; // performative type
		}
		//
		// Load list of performative fields
		performativeFields = primitivesManager.decode(frameBody, descriptor.getSize() + 1);
		//
		size = descriptor.getSize() + performativeFields.getSize() + 1;
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

	@Override
	public String toString()
	{
		StringBuilder response = new StringBuilder(1024);
		response.append("-- PERFOMATIVE --").append(EOL);
		response.append("Size: " + size + " bytes").append(EOL);
		response.append("Descriptor: " + descriptor).append(EOL);
		response.append("DomainId: " + domainId + " descriptorId: " + descriptorId + " (").append(EOL);
		response.append(FRAME_DESCRIPTOR.getByValue(descriptorId) + ")").append(EOL);
		response.append("Fields: " + performativeFields.getSize() + " bytes").append(EOL);
		response.append(performativeFields).append(EOL);
		//
		return response.toString();
	}
}
