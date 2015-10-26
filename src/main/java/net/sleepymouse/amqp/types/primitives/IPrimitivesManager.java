/**
 * 
 */
package net.sleepymouse.amqp.types.primitives;

import net.sleepymouse.amqp.types.formattypes.AMQPType;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public interface IPrimitivesManager
{
	/**
	 * Load the primitive type definitions from XML
	 * 
	 * @return True if ok, else false (Probably fatal)
	 */
	boolean load();

	/**
	 * Decode a primitive type into a usable data structure
	 * 
	 * @param frameBody
	 *            Frame data
	 * @param offset
	 *            Where to start decoding from
	 * @return Decoded type data structure
	 */
	AMQPType decode(byte[] frameBody, int offset) throws FrameFormatException;
}
