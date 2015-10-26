/**
 * 
 */
package net.sleepymouse.amqp.frames;

import java.nio.ByteBuffer;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.sleepymouse.amqp.AMQPConstants;
import net.sleepymouse.amqp.types.primitives.IPrimitivesManager;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
@Component
public class FrameDecoder implements IFrameDecoder
{
	@Inject
	private IPrimitivesManager primitivesManager;

	public FrameDecoder()
	{}

	/**
	 * Decode an AMWP frame from byte stream
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
		// Get payload
		int frameBodySize = size - 4 * doff;
		byte[] frameBody = new byte[(int) frameBodySize];
		buffer.get(frameBody);
		//
		
		if (frameBody[0] != 0x00)
		{
			throw new FrameFormatException("Malformed frame. Frame constructor not begining with 0x00");
		}
		Performative performative = new Performative(frameBody, primitivesManager);
		
		
		return new Frame(size, doff, type, channel, ignored, frameBody, performative);

	}
}
