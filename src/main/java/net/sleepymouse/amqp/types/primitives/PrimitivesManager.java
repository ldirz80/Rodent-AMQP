/**
 * 
 */
package net.sleepymouse.amqp.types.primitives;

import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.*;

import org.slf4j.*;
import org.springframework.stereotype.Component;

import net.sleepymouse.amqp.SystemConstants;
import net.sleepymouse.amqp.spring.managers.logManager.*;

/**
 * @author Alan Smithee
 *
 */
@Component

public class PrimitivesManager implements IPrimitivesManager
{
	private Logger		logger	= LoggerFactory.getLogger(PrimitivesManager.class);

	@Inject
	private ILogManager	logManager;
	private Primitives	primitives;

	public PrimitivesManager()
	{}

	/**
	 * Load the primitive type definitions from XML
	 * 
	 * @return True if ok, else false (Probably fatal)
	 */
	@Override
	public boolean load()
	{
		JAXBContext jaxbContext;
		try
		{
			jaxbContext = JAXBContext.newInstance(Primitives.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = this.getClass().getResourceAsStream("/primitive_types.xml");
			primitives = (Primitives) jaxbUnmarshaller.unmarshal(is);
			logSupportedTypes();
			return true;
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			logManager.error(LoggerFactory.getLogger(LogManager.class), e.getMessage(),
					SystemConstants.LOG_SUB_SYSTEM.PRIMITIVES.name());
		}
		return false;
	}

	/**
	 * Log the loaded supported types
	 */
	public void logSupportedTypes()
	{
		primitives.getTypes().stream().sorted((a, b) -> a.getName().compareTo(b.getName())).forEach(
				e -> logManager.info(logger, "Type: " + e.getName(), SystemConstants.LOG_SUB_SYSTEM.PRIMITIVES.name()));
	}

}
