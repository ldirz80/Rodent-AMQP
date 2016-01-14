/**
 * 
 */
package net.sleepymouse.amqp.spring.components.performatives;

import java.util.Set;

import javax.xml.bind.annotation.*;

/**
 * @author Alan Smithee
 *
 */
@XmlRootElement(name = "performatives")
public class Performatives
{
	private Set<PerformativeType> types;

	/**
	 * @return the types
	 */
	@XmlElement(name = "type")
	public Set<PerformativeType> getTypes()
	{
		return types;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(Set<PerformativeType> types)
	{
		this.types = types;
	}

}
