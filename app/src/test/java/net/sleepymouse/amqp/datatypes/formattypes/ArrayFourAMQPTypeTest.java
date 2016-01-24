package net.sleepymouse.amqp.datatypes.formattypes;

import org.junit.*;

import net.sleepymouse.amqp.datatypes.formattypes.AMQPType;
import net.sleepymouse.amqp.spring.components.primitives.PrimitivesManager;
import net.sleepymouse.amqp.utilities.FrameFormatException;

public class ArrayFourAMQPTypeTest
{

	private int[] arrayMessage = new int[] { 0xF0, // array 32
			0x00, 0x00, 0x00, 0x17, // size
			0x00, 0x00, 0x00, 0x03, // count
			0xA1, // constructor
			0x05, 0x41, 0x42, 0x43, 0x44, 0x45, // string ABCDE
			0x04, 0x46, 0x47, 0x48, 0x49, // string FGHI
			0x02, 0x4A, 0x4B // string JK
	};

	@Before
	public void setUp() throws Exception
	{
		// TODO Auto-generated method stub

	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public final void testArrayFourAMQPType()
	{
		PrimitivesManager primitivesManager = new PrimitivesManager();
		primitivesManager.load();
		byte[] message = generateMessage(arrayMessage);
		AMQPType type = null;
		try
		{
			type = primitivesManager.decode(message, 0);
		}
		catch (FrameFormatException e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
			return;
		}
		//
		System.out.println(type);
	}

	private byte[] generateMessage(int[] data)
	{
		byte[] message = new byte[data.length];
		for (int i = 0; i < data.length; i++)
		{
			message[i] = (byte) data[i];
		}
		return message;

	}
}
