/**
 * 
 */
package net.sleepymouse.amqp.utilities;

import org.junit.*;

/**
 * @author Alan Smithee
 *
 */
public class TextUtilsTest
{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{}

	/**
	 * Test method for {@link net.sleepymouse.amqp.utilities.TextUtils#toUTF32BE(byte[])}.
	 */
	@Test
	public final void testToUTF32BE()
	{
		byte[] utf32 = new byte[] { //
				0x00, 0x00, 0x00, 0x21, //
				0x00, 0x00, 0x00, 0x22, //
				0x00, 0x00, 0x00, 0x23, //
				0x00, 0x00, 0x00, 0x24, //
				0x00, 0x00, 0x00, 0x25, //
				0x00, 0x00, 0x00, 0x26 //
		};

		System.out.println(TextUtils.toUTF32BE(utf32));
	}

}
