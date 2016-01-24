/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class ListFourAMQPType extends ListAMQPType
{

	public ListFourAMQPType(EncodingType encodingType, int count)
	{
		super(encodingType, count);
	}

	@Override
	public String toValue()
	{
		return "list<4>(" + getCount() + ") " + super.toValue();
	}
}
