/**
 * 
 */
package net.sleepymouse.amqp.spring.services.network;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.sleepymouse.amqp.spring.components.initalconnection.IAMQPInitialConnectionHandler;

/**
 * @author Alan Smithee
 *
 */
@Service
public class NetworkService implements INetworkService
{
	@Inject
	private IAMQPInitialConnectionHandler	amqpInitialConnectionHandler;
	//
	private EventLoopGroup					bossGroup	= new NioEventLoopGroup();
	private EventLoopGroup					workerGroup	= new NioEventLoopGroup();
	private ChannelFuture					f;
	//
	@Value("${amqp.port:5000}")
	private int								port;

	/**
	 * Tell the service to initiate itself
	 * 
	 * @return True if start ok, else false
	 */
	@Override
	public boolean start()
	{
		try
		{
			exec();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Tell the service to shutdown
	 * 
	 * @return True if start ok, else false
	 */
	@Override
	public boolean stop()
	{
		f.channel().close();
		return true;
	}

	@Override
	public void exec() throws Exception
	{
		try
		{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)//
					.childHandler(amqpInitialConnectionHandler) //
					.option(ChannelOption.SO_BACKLOG, 128) //
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			f = b.bind(port).sync();

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
