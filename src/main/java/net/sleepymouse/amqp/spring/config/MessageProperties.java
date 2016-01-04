/**
 * 
 */
package net.sleepymouse.amqp.spring.config;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Alan Smithee
 *
 */
@Configuration
public class MessageProperties
{
	@Resource
	private Properties p;

	/**
	 * 
	 */
	public MessageProperties()
	{}

	@Bean
	public PropertiesFactoryBean propertiesFactoryBean() throws Exception
	{
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		ClassPathResource resource = new ClassPathResource("messages.properties");
		propertiesFactoryBean.setLocation(resource);
		return propertiesFactoryBean;
	}

}
