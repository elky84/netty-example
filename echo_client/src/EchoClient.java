import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
    // 호스트를 정의합니다. 로컬 루프백 주소를 지정합니다.
    private static final String HOST = "127.0.0.1";
    // 접속할 포트를 정의합니다.
    private static final int PORT = 10800;
    // 메시지 사이즈를 결정합니다.
    static final int MESSAGE_SIZE = 256;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline cp = sc.pipeline();
                            cp.addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture cf = b.connect(HOST, PORT).sync();
            cf.channel().closeFuture().sync();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            group.shutdownGracefully();
        }
    }
}