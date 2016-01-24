/**
 * 
 */
package net.sleepymouse.amqp.spring.components.performatives;

import javax.xml.bind.annotation.*;

/**
 * @author Alan Smithee
 *
 */
@XmlRootElement(name = "field")
public class Field
{
	private String	name;
	private String	type;
	private Boolean	mandatory;
	private String	defaultValue;
	private Boolean	multiple;

	/**
	 * @return the name
	 */
	@XmlAttribute(name = "name")
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the type
	 */
	@XmlAttribute(name = "type")
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the mandatory
	 */
	@XmlAttribute(name = "mandatory")
	public Boolean getMandatory()
	{
		return mandatory;
	}

	/**
	 * @param mandatory
	 *            the mandatory to set
	 */
	public void setMandatory(Boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	/**
	 * @return the defaultValue
	 */
	@XmlAttribute(name = "default")
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the multiple
	 */
	@XmlAttribute(name = "multiple")
	public Boolean getMultiple()
	{
		return multiple;
	}

	/**
	 * @param multiple
	 *            the multiple to set
	 */
	public void setMultiple(Boolean multiple)
	{
		this.multiple = multiple;
	}

	@Override
	public String toString()
	{
		return "<field name=\"" + name + "\"/>";
	}

}
