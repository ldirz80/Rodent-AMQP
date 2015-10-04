/**
 * 
 */
package net.sleepymouse.amqp.types.primitives;

import javax.xml.bind.annotation.*;

/**
 * @author Alan Smithee
 *
 */
@XmlRootElement(name = "encoding")
public class Encoding
{
	private String	name;
	private String	label;
	private String	code;
	private String	category;
	private String	width;

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
	 * @return the code
	 */
	@XmlAttribute(name = "code")
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return the category
	 */
	@XmlAttribute(name = "category")
	public String getCategory()
	{
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return the width
	 */
	@XmlAttribute(name = "width")
	public String getWidth()
	{
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width)
	{
		this.width = width;
	}

}