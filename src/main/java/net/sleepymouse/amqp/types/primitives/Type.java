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
@XmlRootElement(name = "type")
public class Type
{
	private String			name;
	private String			label;
	private String			primitiveClass;
	private String			provides;
	private Set<Encoding>	encoding;

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
	 * @return the label
	 */
	@XmlAttribute(name = "label")
	public String getLabel()
	{
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * @return the primitiveClass
	 */
	@XmlAttribute(name = "class")
	public String getPrimitiveClass()
	{
		return primitiveClass;
	}

	/**
	 * @param primitiveClass
	 *            the primitiveClass to set
	 */
	public void setPrimitiveClass(String primitiveClass)
	{
		this.primitiveClass = primitiveClass;
	}

	/**
	 * @return the provides
	 */
	@XmlAttribute(name = "provides")
	public String getProvides()
	{
		return provides;
	}

	/**
	 * @param provides
	 *            the provides to set
	 */
	public void setProvides(String provides)
	{
		this.provides = provides;
	}

	/**
	 * @return the encoding
	 */
	@XmlElement(name = "encoding")
	public Set<Encoding> getEncoding()
	{
		return encoding;
	}

	/**
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(Set<Encoding> encoding)
	{
		this.encoding = encoding;
	}

}
