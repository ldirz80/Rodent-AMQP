/**
 * 
 */
package net.sleepymouse.amqp.spring.components.pojo;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.sleepymouse.amqp.spring.services.framedecoder.IFrameDecoder;

/**
 * Decode an AMQP frame into a POJO for further processing
 * 
 * @author Alan Smithee
 *
 */
@Component
public class POJOConverter extends ByteToMessageDecoder implements IPOJOConverter
{
	@Inject
	private IFrameDecoder frameDecoder;

	/**
	 * Return {@code true} if the implementation is {@link Sharable} and so can be added to different
	 * {@link ChannelPipeline}s.
	 */
	public boolean isSharable()
	{
		return false;
	}

	/**
	 * Decode the from one {@link ByteBuf} to an other. This method will be called till either the input {@link ByteBuf}
	 * has nothing to read when return from this method or till nothing was read from the input {@link ByteBuf}.
	 *
	 * @param ctx
	 *            the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
	 * @param in
	 *            the {@link ByteBuf} from which to read data
	 * @param out
	 *            the {@link List} to which decoded messages should be added
	 * @throws Exception
	 *             is thrown if an error occurs
	 */
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		byte[] frame = new byte[in.readableBytes()];
		in = in.readBytes(frame);
		in.discardReadBytes();
		out.add(frameDecoder.decode(frame));
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
