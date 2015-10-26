package net.sleepymouse.amqp.frames;

import org.junit.*;

import net.sleepymouse.amqp.types.primitives.*;
import net.sleepymouse.amqp.utilities.FrameFormatException;

public class PerformativeTest
{

	@Before
	public void setUp() throws Exception
	{}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public final void test()
	{
		IPrimitivesManager pm = new PrimitivesManager();
		try
		{
			Performative performative = new Performative(new byte[0], pm);
		}
		catch (FrameFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
