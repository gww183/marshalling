package marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

public class SubReqServerHandler extends ChannelHandlerAdapter implements
		ChannelInboundHandler {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("active");
		for(int i = 0; i< 10; i++) {
			Order order = new Order();
			order.setId(i);
			order.setProdName("prodName" + i);
			order.setCreater("creater---" + i);
			ctx.writeAndFlush(order);
		}
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext arg0) throws Exception {

	}

	@Override
	public void channelRead(ChannelHandlerContext arg0, Object msg)
			throws Exception {
		System.out.println("read");
		System.out.println("server receive message :" + msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		ctx.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext arg0) throws Exception {

	}

	@Override
	public void channelUnregistered(ChannelHandlerContext arg0)
			throws Exception {

	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext arg0)
			throws Exception {

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1)
			throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	

}
