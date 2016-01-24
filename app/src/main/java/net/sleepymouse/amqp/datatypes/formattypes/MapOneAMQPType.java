/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class MapOneAMQPType extends MapAMQPType
{

	public MapOneAMQPType(EncodingType encodingType, int count)
	{
		super(encodingType, count);
	}

	@Override
	public String toValue()
	{
		return "map<1>(" + getCount() + ")"+super.toValue();	}
}
