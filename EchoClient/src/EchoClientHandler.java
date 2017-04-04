import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EchoClientHandler extends ChannelInboundHandlerAdapter{
    private final ByteBuf message;

    // 초기화
    public EchoClientHandler(){
        message = Unpooled.buffer(EchoClient.MESSAGE_SIZE);
        // 예제로 사용할 바이트 배열을 만듭니다.
        byte[] str = "sign_in".getBytes();
        // 예제 바이트 배열을 메시지에 씁니다.
        message.writeBytes(str);
    }

    // 채널이 활성화 되면 동작할 코드를 정의합니다.
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 메시지를 쓴 후 플러쉬합니다.
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
        ByteBuf byteBufMessage = (ByteBuf) msg;
        int size = byteBufMessage.readableBytes();

        byte [] byteMessage = new byte[size];
        for(int i = 0 ; i < size; i++){
            byteMessage[i] = byteBufMessage.getByte(i);
        }

        String str = new String(byteMessage);

        System.out.println(str);

        System.out.print("Message : ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        ByteBuf echoMsg = Unpooled.buffer(EchoClient.MESSAGE_SIZE);
        echoMsg.writeBytes(input.getBytes());

        ctx.writeAndFlush(echoMsg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}