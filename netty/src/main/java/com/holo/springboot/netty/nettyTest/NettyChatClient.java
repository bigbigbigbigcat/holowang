package com.holo.springboot.netty.nettyTest;

import com.holo.springboot.netty.nettyTest.encode.Client;
import com.holo.springboot.netty.nettyTest.encode.MessageEncode;
import com.holo.springboot.netty.nettyTest.encode.MyMessageDecode;
import com.holo.springboot.netty.nettyTest.encode.MyMessageEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

public class NettyChatClient {
    public static void main(String[] args) throws Exception {
        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(group) //设置线程组
                    .channel(NioSocketChannel.class) // 使用 NioSocketChannel 作为客户端的通道实现
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
//                            pipeline.addLast(new DelimiterBasedFrameDecoder(10240, Unpooled.copiedBuffer("||".getBytes())));
                            pipeline.addLast(new MyMessageEncode());
                            pipeline.addLast(new MyMessageDecode());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
//                            pipeline.addLast(new ObjectEncoder());
//                            pipeline.addLast(new ObjectDecoder(10240, ClassResolvers.cacheDisabled(null)));

                            //加入处理器
//                            pipeline.addLast(new NettyClientObjectHandler());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("netty client start");
            //启动客户端去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            // 判断是否还有输入
           /* while (scanner.hasNextLine()) {
                String str1 = scanner.nextLine();
//                ByteBuf buf = Unpooled.copiedBuffer(str1, CharsetUtil.UTF_8);
                channel.writeAndFlush(str1);
            }*/

            for (int i = 0; i < 200; i++) {
                MessageEncode me = new MessageEncode();
                me.setLength("hello,王骥威".length());
                me.setContext("hello,王骥威".getBytes());
//                channel.writeAndFlush("hello,王骥威||");
                channel.writeAndFlush(me);
            }

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
