/**
 * 
 */
package net.sleepymouse.amqp.transport;

/**
 * @author Alan Smithee
 *
 */
public class Link
{
	private String	name;
	private String	state;	// no idea what this is yet
	private Source	source;
	private Target	target;

	private long	timeout;

	/**
	 * @return the name
	 */
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
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return the source
	 */
	public Source getSource()
	{
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Source source)
	{
		this.source = source;
	}

	/**
	 * @return the target
	 */
	public Target getTarget()
	{
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(Target target)
	{
		this.target = target;
	}

	/**
	 * @return the timeout
	 */
	public long getTimeout()
	{
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(long timeout)
	{
		this.timeout = timeout;
	}
}
