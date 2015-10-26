/**
 * 
 */
package net.sleepymouse.amqp.frames;

import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public interface IFrameDecoder
{
	/**
	 * Decode an AMWP frame from byte stream
	 * 
	 * @param frame
	 *            Frame to decode
	 * @throws FrameFormatException
	 *             If frame malformed
	 */
	Frame decode(byte[] frame) throws FrameFormatException;
}
