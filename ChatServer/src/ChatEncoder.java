import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.*;

public class ChatEncoder extends MessageToByteEncoder<ChatData> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ChatData msg, ByteBuf out) {
        byte[] bytes = msg.getBytes();
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}