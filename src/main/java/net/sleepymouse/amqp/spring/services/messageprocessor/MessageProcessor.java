/**
 * 
 */
package net.sleepymouse.amqp.spring.services.messageprocessor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import io.netty.channel.*;
import net.sleepymouse.amqp.spring.services.framedecoder.*;

/**
 * @author Alan Smithee
 *
 */
@Service
@Scope("prototype")
public class MessageProcessor extends ChannelHandlerAdapter implements IMessageProcessor, ChannelHandler
{

	/**
	 * Return {@code true} if the implementation is {@link Sharable} and so can be added to different
	 * {@link ChannelPipeline}s.
	 */
	public boolean isSharable()
	{
		return false;
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward to the next {@link ChannelHandler} in the
	 * {@link ChannelPipeline}.
	 *
	 * Sub-classes may override this method to change behavior.
	 */
	@Override
	public void channelActive(final ChannelHandlerContext ctx)
	{
		ctx.fireChannelActive();
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to the next {@link ChannelHandler} in the
	 * {@link ChannelPipeline}.
	 *
	 * Sub-classes may override this method to change behaviour.
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		Frame frame = (Frame) msg;
		System.out.println("Process message");
		System.out.println(frame);
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward to the next {@link ChannelHandler}
	 * in the {@link ChannelPipeline}.
	 *
	 * Sub-classes may override this method to change behavior.
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
