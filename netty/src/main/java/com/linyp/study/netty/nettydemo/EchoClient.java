package com.linyp.study.netty.nettydemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author Yinpeng Lin
 * @date 2017/10/27
 * 客户端类
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
            return;
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host, port).start();
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();                                           // 创建Bootstrap
            b.group(group)                                                           // 指定EventLoopGroup以处理客户端事件
                    .channel(NioSocketChannel.class)                                // 适用于NIO传输的Channel类型
                    .remoteAddress(new InetSocketAddress(host, port))               // 设置服务器的InetSocketAddress
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());         // 在创建Channel时，向ChannelPipeline中添加一个EchoClientHandler实例
                        }
                    });
            ChannelFuture f = b.connect().sync();                                   // 连接到远程节点，阻塞等待直到连接完成
            f.channel().closeFuture().sync();                                       // 阻塞，直到Channel关闭
        } finally {
            group.shutdownGracefully().sync();                                      // 关闭线程池并且释放所有的资源
        }
    }
}
