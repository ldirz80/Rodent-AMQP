/**
 * 
 */
package net.sleepymouse.amqp.spring.components.initalconnection;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import net.sleepymouse.amqp.spring.components.pojoconverter.IPOJOConverter;
import net.sleepymouse.amqp.spring.components.versionnegotation.IVersionNegotiationHandler;
import net.sleepymouse.amqp.spring.services.messageprocessor.IMessageProcessor;

/**
 * @author Alan Smithee
 *
 *         Set up the initial connection
 */
@Component
public class AMQPInitialConnectionHandler extends ChannelInitializer<SocketChannel>
		implements IAMQPInitialConnectionHandler
{
	private final static int		LENGTH_FIELD_OFFSET		= 0;
	private final static int		LENGTH_FIELD_LENGTH		= 4;
	private final static int		LENGTH_ADJUSTMENT		= -4;	// Size is included in message length
	private final static int		INITIAL_BYTES_TO_STRIP	= 0;
	private static final boolean	FAIL_FAST				= true;
	//
	@Value("${amqp.maxFrameLength}")
	private int						maxFrameLength;
	//
	@Inject
	private ApplicationContext		applicationContext;

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
		// Need a new one each time - Force spring to create here
		IMessageProcessor messageProcessor = applicationContext.getBean(IMessageProcessor.class);
		IPOJOConverter pojoConverter = applicationContext.getBean(IPOJOConverter.class);
		IVersionNegotiationHandler versionNegotiationHandler = applicationContext
				.getBean(IVersionNegotiationHandler.class);
		//
		ch.pipeline().addLast(versionNegotiationHandler); // AMQP version identification
		//
		ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxFrameLength, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
				LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP, FAIL_FAST)); // Break out AMQP frame
		ch.pipeline().addLast(pojoConverter); // Convert to POJO
		ch.pipeline().addLast(messageProcessor); // Process
	}
}
