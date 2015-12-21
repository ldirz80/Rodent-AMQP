/**
 * 
 */
package net.sleepymouse.amqp.utilities;

import static java.lang.Math.toIntExact;

import java.math.BigInteger;

/**
 * @author Alan Smithee
 *
 */
public final class NumberUtils
{
	private NumberUtils()
	{}

	public static short getShort(byte[] val, int offset)
	{
		return (short) getLong(val, offset, 2);
	}

	public static int getInteger(byte[] val, int offset)
	{
		return (int) getLong(val, offset, 4);
	}

	public static long getLong(byte[] val, int offset)
	{
		return getLong(val, offset, 8);
	}

	public static long getLong(byte[] val, int offset, int bytesToProcess)
	{
		long result = 0;
		bytesToProcess = bytesToProcess + offset;
		for (int i = offset; i < bytesToProcess; i++)
		{
			result = result << 8 | Byte.toUnsignedLong(val[i]);
		}
		return result;
	}

	public static long getLong(byte[] val)
	{
		return getLong(val, 0, val.length);
	}

	/**
	 * Convert an arbitrary sized unsigned value into its string equivalent
	 * 
	 * @param val
	 *            Byte array representing the integer
	 * @return String representation
	 */
	public static String toUnsignedInteger(byte val[])
	{
		return new BigInteger(1, val).toString();
	}

	/**
	 * Convert an arbitrary sized signed value into its string equivalent
	 * 
	 * @param val
	 *            Byte array representing the integer
	 * @return String representation
	 */
	public static String toSignedInteger(byte val[])
	{
		return new BigInteger(val).toString();
	}

	/**
	 * Convert an arbitrary sized signed value into its string equivalent
	 * 
	 * @param val
	 *            Byte array representing the integer
	 * @return String representation
	 */
	public static String toTimestamp(byte val[])
	{
		return Long.toString(packBitsIntoLong(val));
	}

	/**
	 * Convert an IEEE 754 32 bit field into a float
	 * 
	 * @param val
	 *            Byte array representing the float
	 * 
	 * @return String representation
	 */
	public static String toFloat754(byte val[])
	{
		float f = Float.intBitsToFloat(packBitsIntoInt(val));
		return Float.toString(f);
	}

	/**
	 * Convert an IEEE 754 64 bit field into a float
	 * 
	 * @param val
	 *            Byte array representing the float
	 * 
	 * @return String representation
	 */
	public static String toDouble754(byte val[])
	{
		double d = Double.longBitsToDouble(packBitsIntoLong(val));
		return Double.toString(d);
	}

	/**
	 * Convert an IEEE 754 32 bit decimal field into a float
	 * 
	 * @param val
	 *            Byte array representing the float
	 * 
	 * @return String representation
	 */
	public static String toDecimal_32_754(byte val[])
	{
		return "decimal 32 754";
	}

	/**
	 * Convert an IEEE 754 64 bit decimal field into a float
	 * 
	 * @param val
	 *            Byte array representing the float
	 * 
	 * @return String representation
	 */
	public static String toDecimal_64_754(byte val[])
	{
		return "decimal 64 754";
	}

	/**
	 * Convert an IEEE 754 128 bit decimal field into a float
	 * 
	 * @param val
	 *            Byte array representing the float
	 * 
	 * @return String representation
	 */
	public static String toDecimal_128_754(byte val[])
	{
		return "decimal 128 754";
	}

	/**
	 * Dump a byte array into its string equivalent
	 * 
	 * @param val
	 *            Byte array
	 * @return String representation
	 */
	public static String dump(byte val[])
	{
		StringBuilder sb = new StringBuilder(val.length * 3);
		for (int i = 0; i < val.length;)
		{
			sb.append(Integer.toHexString(val[i] >> 4 & 0x0F));
			sb.append(Integer.toHexString(val[i++] & 0x0F));
			sb.append(' ');
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static long packBitsIntoLong(byte[] val)
	{
		if (val.length > 8)
			throw new ArithmeticException("integer overflow");
		long result = 0;
		for (int i = 0; i < val.length; i++)
		{
			result = result << 8 | Byte.toUnsignedInt(val[i]);
		}
		return result;
	}

	public static int packBitsIntoInt(byte[] val)
	{
		return toIntExact(packBitsIntoLong(val));
	}
}
