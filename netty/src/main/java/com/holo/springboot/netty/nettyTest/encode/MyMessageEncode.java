package com.holo.springboot.netty.nettyTest.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncode extends MessageToByteEncoder<MessageEncode> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageEncode msg, ByteBuf out) throws Exception {
        out.writerIndex(msg.getLength());
        out.writeBytes(msg.getContext());
    }
}
