/**
 * 
 */
package net.sleepymouse.amqp.spring.components.performatives;

import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
public interface IPerformativesManager
{
	/**
	 * Load the performatives type definitions from XML
	 * 
	 * @return True if ok, else false (Probably fatal)
	 */
	boolean load();

	/**
	 * Decode a performative type into a usable data structure
	 * 
	 * @param descriptor
	 *            descriptor
	 * @return Decoded type data structure
	 */
	public PerformativeType decode(long descriptor) throws FrameFormatException;

}
