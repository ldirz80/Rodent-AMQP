/**
 * Service to handle type operations
 */
package net.sleepymouse.amqp.spring.services;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import net.sleepymouse.amqp.types.primitives.IPrimitivesManager;

/**
 * @author Alan Smithee
 *
 */
@Service
public class TypeService implements IService
{

	@Inject
	private ApplicationContext	context;
	@Inject
	private IPrimitivesManager	primitivesManager;

	/**
	 * Tell the service to initiate itself
	 * 
	 * @return True if start ok, else false
	 */
	@Override
	public boolean start()
	{
		boolean result = primitivesManager.load();
		//
		return result;
	}

}
