package marshalling;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {
	
	
	public static void main(String[] arg) {
		int port = 8081;
		SubReqServer subReqServer = new SubReqServer();
		try {
			subReqServer.bind(port);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void bind(int port) throws InterruptedException {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.group(bossGroup, workGroup);
			bootStrap.channel(NioServerSocketChannel.class);
			bootStrap.option(ChannelOption.SO_BACKLOG, 1024);
			bootStrap.handler(new LoggingHandler(LogLevel.INFO));
			bootStrap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
					socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					socketChannel.pipeline().addLast(new SubReqServerHandler());
				}
			});
			
			ChannelFuture future = bootStrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
}
