/**
 * 
 */
package net.sleepymouse.amqp.spring.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.sleepymouse.amqp.AMQPConstants;
import net.sleepymouse.amqp.network.*;

/**
 * @author Alan Smithee
 *
 */
@Service
public class NetworkService implements INetworkService
{
	@Inject
	private IAMQPInitialConnectionHandler amqpInitialConnectionHandler;

	/**
	 * Tell the service to initiate itself
	 * 
	 * @return True if start ok, else false
	 */
	public boolean start()
	{
		try
		{
			run();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	@Override
	public void run() throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					.childHandler(amqpInitialConnectionHandler) //
					.option(ChannelOption.SO_BACKLOG, 128) // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(AMQPConstants.PORT).sync(); // (7)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		}
		finally
		{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
