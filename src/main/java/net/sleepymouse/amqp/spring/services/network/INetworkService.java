/**
 * 
 */
package net.sleepymouse.amqp.spring.services.network;

import net.sleepymouse.amqp.spring.services.IService;

/**
 * @author Alan Smithee
 *
 */
public interface INetworkService extends IService
{
	void run() throws Exception;

}
