package com.holo.springboot.netty.nettyTest;

import com.holo.springboot.netty.nettyTest.encode.Client;
import com.holo.springboot.netty.nettyTest.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServerObjectHandler extends ChannelInboundHandlerAdapter {
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("有客户端连上了我这个服务端,remoteAddress:%s,localAddress:%s", ctx.channel().remoteAddress(), ctx.channel().localAddress()));
        channelGroup.add(ctx.channel());
    }

    /**
     * 读取客户端发送的数据
     *
     * @param ctx 上下文对象, 含有通道channel，管道pipeline
     * @param msg 就是客户端发送的数据
     * @throws Exception
     */
  /*  @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        //Channel channel = ctx.channel();
        //ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链接, 出站入站
        //将 msg 转成一个 ByteBuf，类似NIO 的 ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
//        System.out.println(ctx.channel().id());

        for (Channel channel : channelGroup){
            if(channel!=ctx.channel()){
                channel.writeAndFlush(String.format("客户端，给你发消息了：%s",buf.toString(CharsetUtil.UTF_8)));
            }
        }

        if(buf.toString(CharsetUtil.UTF_8).equals("9")){
            //这样可以关系netty的客户端
            ctx.channel().close();
        }
    }*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object s) throws Exception {
//        for (Channel channel : channelGroup){
//            if(channel!=ctx.channel()){
//                channel.writeAndFlush(String.format("客户端，给你发消息了：%s",s));
//        Client c = new
//        ctx.channel().writeAndFlush(s);
//            }
//        }
        ByteBuf b = (ByteBuf) s;
        byte[] bytes = new byte[b.readableBytes()];
        b.readBytes(bytes);


        System.out.println("收到客户端的消息:" + ProtostuffUtil.deserializer(bytes, Client.class));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("客户端%s下线了", ctx.channel().id()));
        channelGroup.remove(ctx.channel());
    }

    /**
     * 数据读取完毕处理方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //客户端消息读取完成后，给客户端回写消息
//        ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
        Client c = new Client();
        c.setClient_name("王骥威");
        c.setId_kind("1");
        c.setId_no("4206000000\n");
        System.out.println(c.toString());
        for (int i = 0; i < 200; i++) {
            ByteBuf buf = Unpooled.copiedBuffer(ProtostuffUtil.serializer(c));
            ctx.writeAndFlush(buf);
        }
    }

    /**
     * 处理异常, 一般是需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
