/**
 * 
 */
package net.sleepymouse.amqp.spring.components.versionnegotation;

import static net.sleepymouse.amqp.AMQPConstants.*;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.slf4j.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import net.sleepymouse.amqp.MessageConstants.*;
import net.sleepymouse.amqp.SystemConstants.LogSubSystem;
import net.sleepymouse.amqp.exception.ProtocolException;
import net.sleepymouse.amqp.spring.components.log.ILogManager;

/**
 * @author Alan Smithee
 *
 */
@Component
@Scope("prototype")
public class VersionNegotationHandler extends ChannelHandlerAdapter implements IVersionNegotiationHandler
{
	private Logger					logger			= LoggerFactory.getLogger(VersionNegotationHandler.class);
	// Only negotiate version once then ignore
	private boolean					negotiate		= true;
	//
	private final int[]				PROTOCOL_HEADER	= {																																																							//
			'A', 'M', 'Q', 'P',																																																													// AMQP
			0x00,																																																																// protocol
																																																																				// id
			0x01, 0x00, 0x00 };																																																													// 1.0.0

	private final static Charset	CHARSET			= Charset.forName("ASCII");
	@Inject
	private ILogManager				logManager;

	private byte[]					versionBuffer	= new byte[8];
	private int						bytesRead		= 0;

	/**
	 * Return {@code true} if the implementation is {@link Sharable} and so can be added to different
	 * {@link ChannelPipeline}s.
	 */
	@Override
	public boolean isSharable()
	{
		return false;
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to the next {@link ChannelHandler} in the
	 * {@link ChannelPipeline}.
	 *
	 * Sub-classes may override this method to change behavior.
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		try
		{
			if (negotiate)
			{
				// Do version negotiation
				ByteBuf buffer = (ByteBuf) msg;
				while ((buffer.readableBytes() > 0) && (bytesRead < 8))
				{
					versionBuffer[bytesRead++] = buffer.readByte();
				}
				//
				if (bytesRead == 8)
				{
					negotiate = false;
					String versionText = new String(versionBuffer, 0, 4, CHARSET);
					if (!"AMQP".equals(versionText))
					{
						// Invalid start to version negotiation data
						String errorMsg = logManager.warn(logger, ProtocolError.PRO_INVALID_AMQP_HEADER,
								LogSubSystem.NETWORK);
						throw new ProtocolException(errorMsg);
					}
					//
					// Protocol id
					if (Byte.toUnsignedInt(versionBuffer[4]) != AMQP_PROTOCOL_ID)
					{
						// Invalid protocol id
						String errorMsg = logManager.warn(logger, ProtocolError.PRO_INVALID_PROTOCOL_ID,
								LogSubSystem.NETWORK);
						throw new ProtocolException(errorMsg);
					}
					//
					// Check version - Major
					if (Byte.toUnsignedInt(versionBuffer[5]) != AMQP_MAJOR)
					{
						// Invalid protocol id
						String errorMsg = logManager.warn(logger, ProtocolError.PRO_UNSUPPORTED_MAJOR,
								LogSubSystem.NETWORK);
						throw new ProtocolException(errorMsg);
					}
					//
					// Check version - Minor
					if (Byte.toUnsignedInt(versionBuffer[6]) != AMQP_MINOR)
					{
						// Invalid protocol id
						String errorMsg = logManager.warn(logger, ProtocolError.PRO_UNSUPPORTED_MINOR,
								LogSubSystem.NETWORK);
						throw new ProtocolException(errorMsg);
					}
					//
					// Check version - Revision
					if (Byte.toUnsignedInt(versionBuffer[7]) != AMQP_REVISION)
					{
						// Invalid protocol id
						String errorMsg = logManager.warn(logger, ProtocolError.PRO_UNSUPPORTED_REVISION,
								LogSubSystem.NETWORK);
						throw new ProtocolException(errorMsg);
					}
					//
					// All good, continue and send own protocol header
					logManager.info(logger, Message.MSG_VERSION, LogSubSystem.NETWORK);
					ctx.writeAndFlush(PROTOCOL_HEADER);
					ctx.fireChannelRead(msg);
				}
			}
			else
			{
				// Just pass the data on
				ctx.fireChannelRead(msg);
			}
		}
		catch (ProtocolException e)
		{
			// Bad version header - send right header and then close
			ctx.writeAndFlush(PROTOCOL_HEADER);
			ctx.close();
		}
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
		if (cause instanceof IOException)
		{
			logManager.error(logger, ServerEvent.SRV_NETWORK, LogSubSystem.NETWORK, cause.getMessage());
		}
		else
		{
			logManager.error(logger, ServerEvent.SRV_UNKNOWN, LogSubSystem.NETWORK, cause.getMessage());
		}
		ctx.close();
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelUnregistered()} to forward to the next {@link ChannelHandler} in
	 * the {@link ChannelPipeline}.
	 *
	 * Sub-classes may override this method to change behavior.
	 */
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
	{
		logManager.info(logger, Message.MSG_CLOSE, LogSubSystem.NETWORK, ctx.channel().id().asLongText());
		ctx.fireChannelUnregistered();
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
		logManager.info(logger, Message.MSG_ACTIVE, LogSubSystem.NETWORK, ctx.channel().id().asLongText());
		ctx.fireChannelActive();
	}

	/**
	 * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward to the next {@link ChannelHandler} in the
	 * {@link ChannelPipeline}.
	 *
	 * Sub-classes may override this method to change behavior.
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception
	{
		logManager.info(logger, Message.MSG_INACTIVE, LogSubSystem.NETWORK, ctx.channel().id().asLongText());
		ctx.fireChannelInactive();
	}

}
