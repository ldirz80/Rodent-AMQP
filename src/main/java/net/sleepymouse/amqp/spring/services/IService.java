/**
 * 
 */
package net.sleepymouse.amqp.spring.services;

/**
 * @author Alan Smithee
 *
 */
public interface IService
{
	/**
	 * Tell the service to initiate itself
	 * 
	 * @return True if start ok, else false
	 */
	default public boolean start()
	{
		return true;
	}

	/**
	 * Tell the service to shutdown
	 * 
	 * @return True if start ok, else false
	 */
	default public boolean stop()
	{
		return true;
	}

}
