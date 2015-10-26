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
import net.sleepymouse.amqp.types.TypeConstants.SubcategoryFormatCodes;
import net.sleepymouse.amqp.types.formattypes.*;
import net.sleepymouse.amqp.utilities.FrameFormatException;

/**
 * @author Alan Smithee
 *
 */
@Component

public class PrimitivesManager implements IPrimitivesManager
{
	private Logger			logger	= LoggerFactory.getLogger(PrimitivesManager.class);

	@Inject
	private ILogManager		logManager;
	private Primitives		primitives;
	private EncodingType[]	types	= new EncodingType[0xFF];

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
			restructureTypes();
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
	 * Find a type by code
	 * 
	 * @param formatCode
	 *            Integer between 0x00 and 0xFF
	 * @return Corresponding type or null if no match available
	 */
	public EncodingType getType(int formatCode)
	{
		return types[formatCode];
	}

	/**
	 * Decode a primitive type into a usable data structure
	 * 
	 * @param frameBody
	 *            Frame data
	 * @param offset
	 *            Where to start decoding from
	 * @return Decoded type data structure
	 */
	public AMQPType decode(byte[] frameBody, int offset) throws FrameFormatException
	{
		EncodingType encodingType = getType(frameBody[offset]);
		if (null == encodingType)
		{
			throw new FrameFormatException("Malformed primitive type. Unrecognised type");
		}
		//
		// Lets see what we have
		//
		SubcategoryFormatCodes subcategoryFormatCodes = encodingType.getEncoding().getSubcategoryFormatCode();
		switch (subcategoryFormatCodes)
		{
			case EMPTY:
				return new EmptyAMQPType(encodingType);
			//
			// <code><data>
			case FIXED_ONE:
				return new FixedOneAMQPType(frameBody, offset, encodingType);

			case FIXED_TWO:
				return new FixedTwoAMQPType(frameBody, offset, encodingType);

			case FIXED_FOUR:
				return new FixedFourAMQPType(frameBody, offset, encodingType);

			case FIXED_EIGHT:
				return new FixedEightAMQPType(frameBody, offset, encodingType);

			case FIXED_SIXTEEN:
				return new FixedSixteenAMQPType(frameBody, offset, encodingType);
			//
			// <code><datasize><data>
			case VARIABLE_ONE:
			{
				return new VariableOneAMQPType(frameBody, offset, encodingType);
			}
			case VARIABLE_FOUR:
			{
				return new VariableFourAMQPType(frameBody, offset, encodingType);
			}
			case ARRAY_ONE:
			{
				return new ArrayOneAMQPType(frameBody, offset, encodingType, this);
			}
			case ARRAY_FOUR:
			{
				return new ArrayFourAMQPType(frameBody, offset, encodingType, this);
			}

			case COMPOUND_FOUR:
			case COMPOUND_ONE:
				break;
		}
		return null;
	}

	/**
	 * Log the loaded supported types
	 */
	private void logSupportedTypes()
	{
		primitives.getTypes().stream().sorted((a, b) -> a.getName().compareTo(b.getName())).forEach(
				e -> logManager.info(logger, "Type: " + e.getName(), SystemConstants.LOG_SUB_SYSTEM.PRIMITIVES.name()));
	}

	/**
	 * Put the types and encodings into a a form that is simple to search
	 */
	private void restructureTypes()
	{
		for (Type type : primitives.getTypes())
		{
			for (Encoding encoding : type.getEncoding())
			{
				EncodingType encodingType = new EncodingType(type, encoding);
				types[encoding.getCodeAsInt()] = encodingType;
			}
		}
	}

}
