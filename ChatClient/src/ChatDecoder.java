import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class ChatDecoder extends ByteToMessageDecoder {

    private final int integerBytes = Integer.SIZE / 8;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int size = in.readableBytes();
        if (size < integerBytes) {
            return;
        }

        int length = in.getInt(0);
        if(length > size - integerBytes) {
            return;
        }

        byte [] bytes = new byte[length];
        for(int i = 0 ; i < length; i++){
            bytes[i] = in.getByte(i + integerBytes);
        }

        out.add(new ChatData(new String(bytes)));
        in.readBytes(integerBytes + length);
    }
}