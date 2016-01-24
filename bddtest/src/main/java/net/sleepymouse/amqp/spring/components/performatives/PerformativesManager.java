/**
 * 
 */
package net.sleepymouse.amqp.spring.components.performatives;

import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.*;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sleepymouse.amqp.SystemConstants;
import net.sleepymouse.amqp.spring.components.log.*;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
@Component

public class PerformativesManager implements IPerformativesManager
{
	@Inject
	private ILogManager		logManager;
	private Performatives	performatives;

	public PerformativesManager()
	{}

	/**
	 * Load the performative type definitions from XML
	 * 
	 * @return True if ok, else false (Probably fatal)
	 */
	@Override
	public boolean load()
	{
		JAXBContext jaxbContext;
		try
		{
			jaxbContext = JAXBContext.newInstance(Performatives.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = this.getClass().getResourceAsStream("/performatives.xml");
			performatives = (Performatives) jaxbUnmarshaller.unmarshal(is);
			return true;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			logManager.error(LoggerFactory.getLogger(LogManager.class), e.getMessage(),
					SystemConstants.LogSubSystem.PERFORMATIVES);
		}
		return false;
	}

	@Override
	public PerformativeType decode(long descriptor) throws FrameFormatException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
