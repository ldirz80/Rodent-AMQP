/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public class MapFourAMQPType extends MapAMQPType
{

	public MapFourAMQPType(EncodingType encodingType, int count)
	{
		super(encodingType, count);
	}

	@Override
	public String toValue()
	{
		return "map<4>(" + getCount() + ")" + super.toValue();
	}
}
