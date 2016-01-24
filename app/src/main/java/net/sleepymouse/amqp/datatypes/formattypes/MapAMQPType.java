/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class MapAMQPType extends AMQPType
{
	private int				count;
	private AMQPType[][]	map;

	public MapAMQPType(EncodingType encodingType, int count)
	{
		super(encodingType);
		this.count = count;
		map = new AMQPType[count][2];
	}

	public int getCount()
	{
		return count;
	}

	public AMQPType[][] getMap()
	{
		return map;
	}

	public void put(int index, AMQPType key, AMQPType value)
	{
		AMQPType[] tuple = new AMQPType[2];
		tuple[0] = key;
		tuple[1] = value;
		map[index] = tuple;
	}

	@Override
	public String toValue()
	{
		StringBuilder sb = new StringBuilder(1024);
		sb.append('[');
		for (int i = 0; i < map.length; i++)
		{
			sb.append('(').append(map[i][0].toValue()).append(',').append(map[i][1].toValue()).append(')');
		}
		sb.append(']');
		return sb.toString();
	}
}
