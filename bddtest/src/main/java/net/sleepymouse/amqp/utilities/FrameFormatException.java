/**
 * 
 */
package net.sleepymouse.amqp.utilities;

/**
 * <p>
 * <code>FrameFormatException</code> represents a generic XPath exception.
 * </p>
 *
 * @author Alan Smithee
 * @since 1.0
 */
public class FrameFormatException extends Exception
{

	/**
	 * <p>
	 * Stream Unique Identifier.
	 * </p>
	 */
	private static final long serialVersionUID = -840188520656781718L;

	/**
	 * <p>
	 * Constructs a new <code>FrameFormatException</code> with the specified detail <code>message</code>.
	 * </p>
	 *
	 * <p>
	 * The <code>cause</code> is not initialized.
	 * </p>
	 *
	 * <p>
	 * If <code>message</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.
	 * </p>
	 *
	 * @param message
	 *            The detail message.
	 *
	 * @throws NullPointerException
	 *             When <code>message</code> is <code>null</code>.
	 */
	public FrameFormatException(String message)
	{
		super(message);
		if (message == null)
		{
			throw new NullPointerException("Message cannot be null");
		}
	}

	/**
	 * <p>
	 * Constructs a new <code>FrameFormatException</code> with the specified <code>cause</code>.
	 * </p>
	 *
	 * <p>
	 * If <code>cause</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.
	 * </p>
	 *
	 * @param cause
	 *            The cause.
	 *
	 * @throws NullPointerException
	 *             if <code>cause</code> is <code>null</code>.
	 */
	public FrameFormatException(Throwable cause)
	{
		super(cause);
		if (cause == null)
		{
			throw new NullPointerException("Cause cannot be null");
		}
	}

}
