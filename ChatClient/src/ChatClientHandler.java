import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChatClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 메시지를 쓴 후 플러쉬합니다.
        ctx.writeAndFlush(new ChatData("서버와 연결에 성공했습니다."));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("서버와 연결이 종료되었습니다.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatData m = (ChatData) msg;
        System.out.println("received : " + m);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("messsage : ");
        String input = br.readLine();

        ctx.writeAndFlush(new ChatData(input));

        // ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}