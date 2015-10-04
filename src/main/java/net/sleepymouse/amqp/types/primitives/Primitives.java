/**
 * 
 */
package net.sleepymouse.amqp.types.primitives;

import java.util.Set;

import javax.xml.bind.annotation.*;

/**
 * @author Alan Smithee
 *
 */
@XmlRootElement(name = "primitives")
public class Primitives
{
	private Set<Type> types;

	/**
	 * @return the types
	 */
	@XmlElement(name = "type")
	public Set<Type> getTypes()
	{
		return types;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(Set<Type> types)
	{
		this.types = types;
	}

}
