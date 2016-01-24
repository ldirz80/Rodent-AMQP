/**
 * 
 */
package net.sleepymouse.amqp.spring.components.performatives;

import static net.sleepymouse.amqp.SystemConstants.EOL;

import java.util.List;

import javax.xml.bind.annotation.*;

/**
 * @author Alan Smithee
 *
 */
@XmlRootElement(name = "type")
public class PerformativeType
{
	private String		name;
	private String		classType;
	private String		source;
	private String		provides;
	private List<Field>	fields;

	/**
	 * @return the name
	 */
	@XmlAttribute(name = "name")
	public String getTypeName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setTypeName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the class type
	 */
	@XmlAttribute(name = "class")
	public String getClassType()
	{
		return classType;
	}

	/**
	 * @param classType
	 *            the class to set
	 */
	public void setClassType(String classType)
	{
		this.classType = classType;
	}

	/**
	 * @return the source
	 */
	@XmlAttribute(name = "source")
	public String getSource()
	{
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source)
	{
		this.source = source;
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

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(512);
		sb.append("<type name=\"" + name + "\" class=\"" + classType + "\">");
		fields.stream().map(e -> e.toString()).forEach(e -> sb.append('\t').append(e).append(EOL));
		sb.append("</type>");
		//
		return sb.toString();
	}
}
