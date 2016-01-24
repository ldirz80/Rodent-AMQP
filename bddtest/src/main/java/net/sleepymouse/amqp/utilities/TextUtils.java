/**
 * 
 */
package net.sleepymouse.amqp.utilities;

import java.io.UnsupportedEncodingException;

/**
 * @author Alan Smithee
 *
 */
public final class TextUtils
{
	private TextUtils()
	{}

	public static String toUTF32BE(byte[] raw)
	{
		try
		{
			return new String(raw, "UTF-32BE");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return "*";
		}
	}

	public static String toUTF8(byte[] raw)
	{
		try
		{
			return new String(raw, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return "*";
		}
	}

	public static String toASCII(byte[] raw)
	{
		try
		{
			return new String(raw, "ASCII");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return "*";
		}
	}

}
