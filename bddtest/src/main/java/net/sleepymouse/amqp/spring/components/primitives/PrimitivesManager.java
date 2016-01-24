/**
 * 
 */
package net.sleepymouse.amqp.spring.components.primitives;

import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.*;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sleepymouse.amqp.SystemConstants;
import net.sleepymouse.amqp.datatypes.TypeConstants.SubcategoryFormatCodes;
import net.sleepymouse.amqp.datatypes.formattypes.*;
import net.sleepymouse.amqp.spring.components.log.*;
import net.sleepymouse.amqp.utilities.*;

/**
 * @author Alan Smithee
 *
 */
@Component

public class PrimitivesManager implements IPrimitivesManager
{
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
					SystemConstants.LogSubSystem.PRIMITIVES);
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
		EncodingType encodingType = getType(Byte.toUnsignedInt(frameBody[offset]));
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
			{
				return decodeCompoundFour(frameBody, offset, encodingType);
			}
			case COMPOUND_ONE:
				return decodeCompoundOne(frameBody, offset, encodingType);
		}
		return null;
	}

	/**
	 * Decode compound types (May be list or map)
	 * 
	 * @param frameBody
	 *            Data being decoded
	 * @param offset
	 *            Where we have go to in the buffer
	 * @param encodingType
	 *            What
	 * @return Decoded compound type
	 * @throws FrameFormatException
	 *             If an illegalframe is encoountered
	 */
	private AMQPType decodeCompoundOne(byte[] frameBody, int offset, EncodingType encodingType)
			throws FrameFormatException
	{
		switch (Byte.toUnsignedInt(frameBody[offset++]))
		{
			case 0xC0: // list8
			{
				int size = Byte.toUnsignedInt(frameBody[offset++]); // number of bytes beyond format code
				int count = Byte.toUnsignedInt(frameBody[offset++]); // number of elements in the list
				ListOneAMQPType list = new ListOneAMQPType(encodingType, count);
				list.setSize(size + 2);
				AMQPType element;
				for (int i = 0; i < count; i++)
				{
					element = decode(frameBody, offset);
					offset = offset + element.getSize();
					list.add(i, element);
				}
				return list;
			}
			case 0xC1: // map8
			{
				int size = Byte.toUnsignedInt(frameBody[offset++]); // number of bytes beyond format code
				int count = Byte.toUnsignedInt(frameBody[offset++]); // number of elements in the map [Total, not pairs
																		// !]
				count = count / 2;
				MapOneAMQPType map = new MapOneAMQPType(encodingType, count);
				map.setSize(size + 2);
				AMQPType key, value;
				for (int i = 0; i < count; i++)
				{
					key = decode(frameBody, offset);
					offset = offset + key.getSize();
					//
					value = decode(frameBody, offset);
					offset = offset + value.getSize();
					map.put(i, key, value);
				}
				return map;
			}
			default:
				throw new FrameFormatException("Malformed primitive type. Unrecognised type");
		}
	}

	private AMQPType decodeCompoundFour(byte[] frameBody, int offset, EncodingType encodingType)
			throws FrameFormatException
	{
		switch (Byte.toUnsignedInt(frameBody[offset]))
		{
			case 0xD0: // list32
			{
				int size = (int) NumberUtils.getLong(frameBody, 4, 4); // number of bytes beyond this long
				int count = (int) NumberUtils.getLong(frameBody, 8, 4); // number of elements in the map [Total, not
																		// pairs !]
				count = count / 2;
				ListOneAMQPType list = new ListOneAMQPType(encodingType, count);
				list.setSize(size + 1 + 4);
				AMQPType element;
				for (int i = 0; i < count; i++)
				{
					element = decode(frameBody, offset);
					offset = offset + element.getSize();
					list.add(i, element);
				}
				return list;
			}
			case 0xD1: // map32
			{
				{
					int size = (int) NumberUtils.getLong(frameBody, 4, 4); // number of bytes beyond this 32 bit word
					int count = (int) NumberUtils.getLong(frameBody, 8, 4); // number of elements in the list
					MapFourAMQPType map = new MapFourAMQPType(encodingType, count);
					map.setSize(size + 1 + 4);
					AMQPType key, value;
					;
					for (int i = 0; i < count; i++)
					{
						key = decode(frameBody, offset);
						offset = offset + key.getSize();
						//
						value = decode(frameBody, offset);
						offset = offset + value.getSize();
						map.put(i, key, value);
					}
					return map;
				}
			}
			default:
				throw new FrameFormatException("Malformed primitive type. Unrecognised type");
		}
	}

	/**
	 * Log the loaded supported types
	 */
	private void logSupportedTypes()
	{
		// primitives.getTypes().stream().sorted((a, b) -> a.getName().compareTo(b.getName())).forEach(
		// e -> logManager.info(logger, "Type: " + e.getName(), SystemConstants.LogSubSystem.PRIMITIVES.name()));
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
