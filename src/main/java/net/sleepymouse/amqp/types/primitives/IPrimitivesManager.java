/**
 * 
 */
package net.sleepymouse.amqp.types.primitives;

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
	public boolean load();

	/**
	 * Log the loaded supported types
	 */
	public void logSupportedTypes();
}
