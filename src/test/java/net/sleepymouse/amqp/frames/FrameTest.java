package net.sleepymouse.amqp.frames;

import org.junit.*;

import net.sleepymouse.amqp.utilities.FrameFormatException;

public class FrameTest
{
	@Before
	public void setUp() throws Exception
	{}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public final void testFrame()
	{
		byte[] testFrame = { 0x00, 0x00, 0x00, 0x14, // Size
				0x03, // DOFF
				0x00, // Type (AMQP)
				0x01, 0x02, // Type Specific
				0x11, 0x12, 0x13, 0x14, // Extended Header
				0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28 // FrameDecoder Body

		};

		IFrameDecoder frameDecoder;
		Frame frame;
		try
		{
			frameDecoder = new FrameDecoder();
			frame = frameDecoder.decode(testFrame);

		}
		catch (FrameFormatException e)
		{
			Assert.fail(e.getMessage());
			return;
		}
		//
		Assert.assertEquals(testFrame.length, frame.getSize());
	}

}
