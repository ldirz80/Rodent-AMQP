/**
 * Service to handle type operations
 */
package net.sleepymouse.amqp.spring.components.types;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.sleepymouse.amqp.spring.components.performatives.IPerformativesManager;
import net.sleepymouse.amqp.spring.components.primitives.IPrimitivesManager;

/**
 * @author Alan Smithee
 *
 */
@Service
public class TypeHandler implements ITypeHandler
{

	@Inject
	private IPrimitivesManager		primitivesManager;

	@Inject
	private IPerformativesManager	performativesManager;

	/**
	 * Tell the service to initiate itself
	 * 
	 * @return True if start ok, else false
	 */
	@Override
	public boolean start()
	{
		boolean result = primitivesManager.load();
		result = result & performativesManager.load();
		//
		return result;
	}

}
