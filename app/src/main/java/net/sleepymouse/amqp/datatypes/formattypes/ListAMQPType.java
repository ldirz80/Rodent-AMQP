/**
 * 
 */
package net.sleepymouse.amqp.datatypes.formattypes;

import java.util.*;

import net.sleepymouse.amqp.spring.components.primitives.EncodingType;

/**
 * @author Alan Smithee
 *
 */
public abstract class ListAMQPType extends AMQPType
{
	private int				count;
	private List<AMQPType>	list;

	public ListAMQPType(EncodingType encodingType, int count)
	{
		super(encodingType);
		this.count = count;
		list = new ArrayList<>(count);
	}

	public int getCount()
	{
		return count;
	}

	public List<AMQPType> getList()
	{
		return list;
	}

	public void add(int index, AMQPType element)
	{
		list.add(index, element);
	}

	public void setSize(int size)
	{
		super.setSize(size);
	}

	@Override
	public String toValue()
	{
		StringBuilder sb = new StringBuilder(1024);
		list.stream().map(e -> e.toValue()).forEach(s -> sb.append('[').append(s).append(']'));
		return sb.toString();
	}

}
