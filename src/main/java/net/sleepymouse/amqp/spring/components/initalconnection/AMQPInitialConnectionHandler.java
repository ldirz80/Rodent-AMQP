/**
 * 
 */
package net.sleepymouse.amqp.spring.components.initalconnection;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import net.sleepymouse.amqp.spring.components.pojo.IPOJOConverter;
import net.sleepymouse.amqp.spring.components.versionnegotiation.IVersionNegotiationHandler;
import net.sleepymouse.amqp.spring.services.messageprocessor.IMessageProcessor;

/**
 * @author Alan Smithee
 *
 */
@Component
public class AMQPInitialConnectionHandler extends ChannelInitializer<SocketChannel>
		implements IAMQPInitialConnectionHandler
{
	private static final int			maxFrameLength		= 1024;
	private static final int			lengthFieldOffset	= 0;
	private static final int			lengthFieldLength	= 4;
	private static final int			lengthAdjustment	= 0;
	private static final int			initialBytesToStrip	= 0;
	private static final boolean		failFast			= true;
	//
	@Inject
	private IPOJOConverter				pojoConverter;
	@Inject
	private IMessageProcessor			messageProcessor;
	@Inject
	private IVersionNegotiationHandler	versionNegotiationHandler;

	/**
	 * This method will be called once the {@link Channel} was registered. After the method returns this instance will
	 * be removed from the {@link ChannelPipeline} of the {@link Channel}.
	 *
	 * @param ch
	 *            the {@link Channel} which was registered.
	 * @throws Exception
	 *             is thrown if an error occurs. In that case the {@link Channel} will be closed.
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		ch.pipeline().addLast(versionNegotiationHandler); // AMQP version identification
		ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip, failFast)); // Decode AMQP frame
		ch.pipeline().addLast(pojoConverter); // Convert to POJO
		ch.pipeline().addLast(messageProcessor); // Process
	}
}
