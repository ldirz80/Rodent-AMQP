/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class ListOneAMQPType extends ListAMQPType
{

	public ListOneAMQPType(EncodingType encodingType, int count)
	{
		super(encodingType, count);
	}

	@Override
	public String toValue()
	{
		return "list<1>(" + getCount() + ") " + super.toValue();
	}
}
