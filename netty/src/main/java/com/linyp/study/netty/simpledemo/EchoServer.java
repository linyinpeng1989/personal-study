package com.linyp.study.netty.simpledemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Lin on 2016/5/12.
 *
 * netty应答服务器类
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws  Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //create ServerBootstrap instance
            ServerBootstrap b = new ServerBootstrap();
            //Specifies NIO transport, local socket address
            //Adds handler to channel pipeline
            b.group(group).channel(NioServerSocketChannel.class).localAddress(port)
                    .childHandler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new EchoServerHandler());
                }
            });
            //Binds server, waits for server to close, and releases resources
            ChannelFuture f = b.bind().sync();  // 绑定服务器等待，调用sync()方法会阻塞直到服务器完成绑定
            System.out.println(EchoServer.class.getName() + "started and listen on “" + f.channel().localAddress() + "”");
            f.channel().closeFuture().sync();   // 服务器等待通道关闭，也会阻塞直到关闭
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoServer(20000).start();
    }
}
