package marshalling;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SubReqClient {

	public static void main(String[] args) {
		int port = 8081;
		SubReqClient client = new SubReqClient();
		try {
			client.connect(port);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(int port) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
			Bootstrap bootStrap = new Bootstrap();
			bootStrap.group(group);
			bootStrap.channel(NioSocketChannel.class);
			bootStrap.option(ChannelOption.TCP_NODELAY, true);
			bootStrap.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
					socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					socketChannel.pipeline().addLast(new SubReqClientHandler());
				}
			});
			ChannelFuture future = bootStrap.connect("127.0.0.1", port).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
		
	}

}
