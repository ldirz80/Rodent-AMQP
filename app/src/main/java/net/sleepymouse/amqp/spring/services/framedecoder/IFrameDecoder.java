/**
 * 
 */
package net.sleepymouse.amqp.spring.services.framedecoder;

import net.sleepymouse.amqp.spring.services.IService;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public interface IFrameDecoder extends IService
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
