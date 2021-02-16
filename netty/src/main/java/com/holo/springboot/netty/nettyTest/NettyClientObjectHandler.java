package com.holo.springboot.netty.nettyTest;

import com.holo.springboot.netty.nettyTest.encode.Client;
import com.holo.springboot.netty.nettyTest.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyClientObjectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf buf = Unpooled.copiedBuffer("HelloServer，我连接成功啦", CharsetUtil.UTF_8);
//        ctx.writeAndFlush(buf);
        Client c = new Client();
        c.setClient_name("我来自客户段");
        c.setId_kind("1");
        c.setId_no("42061111111");
        ByteBuf buf = Unpooled.copiedBuffer(ProtostuffUtil.serializer(c));
        ctx.writeAndFlush(buf);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("读取完了服务段的消息");

    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("收到服务端的消息:" + ((Client)msg).toString());
//        System.out.println("服务端的地址： " + channelHandlerContext.channel().remoteAddress());
        ByteBuf b = (ByteBuf) msg;
        byte[] bytes = new byte[b.readableBytes()];
        b.readBytes(bytes);

        System.out.println("收到服务端的消息:" + ProtostuffUtil.deserializer(bytes, Client.class));
    }
}
