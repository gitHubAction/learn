package netty.codec.messagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2023/2/8 10:55
 */
public class MsgPackDecode extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int len = byteBuf.readableBytes();
        byte[] dst = new byte[len];
        MessagePack msgPack = new MessagePack();
        byteBuf.getBytes(byteBuf.readerIndex(),dst,0, len);

        list.add(msgPack.read(dst));
    }
}
