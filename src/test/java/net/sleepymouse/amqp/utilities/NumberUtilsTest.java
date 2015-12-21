/**
 * 
 */
package net.sleepymouse.amqp.utilities;

import org.junit.*;

/**
 * @author Alan Smithee
 *
 */
public class NumberUtilsTest
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
	 * Test method for {@link net.sleepymouse.amqp.utilities.NumberUtils#getLong(byte[], int)}.
	 */
	@Test
	public final void testGetLongByteArrayInt()
	{
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sleepymouse.amqp.utilities.NumberUtils#getLong(byte[], int, int)}.
	 */
	@Test
	public final void testGetLongByteArrayIntInt()
	{
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sleepymouse.amqp.utilities.NumberUtils#toUnsignedInteger(byte[])}.
	 */
	@Test
	public final void testToUnsignedInteger()
	{
		// fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sleepymouse.amqp.utilities.NumberUtils#dump(byte[])}.
	 */
	@Test
	public final void testDump()
	{
		byte[] data = new byte[] { 0x00, 0x7F, -1, -128 };
		String result = NumberUtils.dump(data);
		Assert.assertEquals(result, "00 7f ff 80");
	}

	/**
	 * Test method for {@link net.sleepymouse.amqp.utilities.NumberUtils#toUnsignedInteger(byte[])}.
	 */
	@Test
	public final void testToTimestamp()
	{
		long t = System.currentTimeMillis();

		byte[] val = new byte[8];
		val[0] = (byte) (t >> 56 & 0xFF);
		val[1] = (byte) (t >> 48 & 0xFF);
		val[2] = (byte) (t >> 40 & 0xFF);
		val[3] = (byte) (t >> 32 & 0xFF);
		val[4] = (byte) (t >> 24 & 0xFF);
		val[5] = (byte) (t >> 16 & 0xFF);
		val[6] = (byte) (t >> 8 & 0xFF);
		val[7] = (byte) (t & 0xFF);
		//
		Assert.assertTrue(Long.toString(t).equals(NumberUtils.toTimestamp(val)));
	}

}
