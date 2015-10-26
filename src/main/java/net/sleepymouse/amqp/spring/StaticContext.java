package net.sleepymouse.amqp.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Service;

@Service
public class StaticContext implements BeanFactoryAware
{
	public static BeanFactory CONTEXT;

	public StaticContext()
	{}

	public static Object getBean(String s) throws BeansException
	{
		return CONTEXT.getBean(s);
	}

	public static <T> T getBean(String s, Class<T> tClass) throws BeansException
	{
		return CONTEXT.getBean(s, tClass);
	}

	public static <T> T getBean(Class<T> tClass) throws BeansException
	{
		return CONTEXT.getBean(tClass);
	}

	public static Object getBean(String s, Object... objects) throws BeansException
	{
		return CONTEXT.getBean(s, objects);
	}

	public static boolean containsBean(String s)
	{
		return CONTEXT.containsBean(s);
	}

	@Override
	public void setBeanFactory(BeanFactory applicationContext) throws BeansException
	{
		CONTEXT = applicationContext;
	}
}