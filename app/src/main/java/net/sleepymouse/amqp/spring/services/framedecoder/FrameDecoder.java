/**
 * 
 */
package net.sleepymouse.amqp.spring.services.framedecoder;

import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.sleepymouse.amqp.AMQPConstants;
import net.sleepymouse.amqp.spring.components.primitives.IPrimitivesManager;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
@Service
public class FrameDecoder implements IFrameDecoder
{
	private IPrimitivesManager primitivesManager;

	public FrameDecoder()
	{}

	@Inject
	public void setPrimitivesManager(IPrimitivesManager primitivesManager)
	{
		this.primitivesManager = primitivesManager;
	}

	/**
	 * Decode an AMQP frame from byte stream
	 * 
	 * @param frame
	 *            Frame to decode
	 * @throws FrameFormatException
	 *             If frame malformed
	 */
	public Frame decode(byte[] frame) throws FrameFormatException
	{
		ByteBuffer buffer = ByteBuffer.wrap(frame);
		int size = buffer.getInt();
		if (size < 0)
		{
			throw new FrameFormatException("Maximum frame size (" + Integer.MAX_VALUE + ") exceeded");
		}
		if (size < 8)
		{
			throw new FrameFormatException("Malformed frame. Minimum size 8. Actual size " + size);
		}
		int doff = Byte.toUnsignedInt(buffer.get());
		if (doff < 2)
		{
			throw new FrameFormatException("Malformed frame data offset (DOFF). Minimum size 2. Actual size " + doff);
		}
		int type = Byte.toUnsignedInt(buffer.get());
		if (type != AMQPConstants.AMQP_TYPE)
		{
			throw new FrameFormatException("FrameDecoder type not AMQP. Type code supplied is " + type);
		}
		int channel = Short.toUnsignedInt(buffer.getShort());
		//
		// Get Extended Header - Ref 2.3.2 AMQP Frames
		int ignoredSize = doff * 4 - 8;
		byte[] ignored = new byte[ignoredSize];
		buffer = buffer.get(ignored);
		//
		// Get frame body and decode the performative
		int frameBodySize = size - 4 * doff;
		byte[] frameBody = new byte[frameBodySize];
		buffer = buffer.get(frameBody);
		Performative performative = new Performative(frameBody, primitivesManager);
		//
		// Get the payload and decode the message
		byte[] payload = Arrays.copyOfRange(frameBody, performative.getSize(), frameBody.length);
		Message message = new Message(payload, primitivesManager);
		//
		return new Frame(size, doff, type, channel, ignored, performative, message);
	}
}
