/**
 * 
 */
package net.sleepymouse.amqp.spring.components.versionnegotiation;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * @author Alan Smithee
 *
 */
public class VersionNegotationHandler extends ChannelHandlerAdapter implements IVersionNegotiationHandler
{
	private boolean negotiate = true;

	/**
	 * Return {@code true} if the implementation is {@link Sharable} and so can be added to different
	 * {@link ChannelPipeline}s.
	 */
	public boolean isSharable()
	{
		return false;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx)
	{
		System.out.println("Channel active");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		try
		{
			if (negotiate)
			{
				ByteBuf buf = (ByteBuf) msg;
				if (buf.readableBytes() >= 8)
				{
					negotiate = false;
					System.out.println("channelRead: Got 8");
					byte[] version = new byte[8];
					buf.readBytes(version);
					for (int i = 0; i < version.length; i++)
					{
						System.out.println(version[i]);
					}
					System.out.println("Remaining: " + buf.readableBytes());

					buf.discardReadBytes();

					ctx.fireChannelRead(msg);
				}
				System.out.println("channelRead: " + msg);
			}
			else
			{
				ctx.fireChannelRead(msg);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			//
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{ // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
