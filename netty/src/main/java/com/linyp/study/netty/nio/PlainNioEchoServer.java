package com.linyp.study.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Lin on 2016/5/26.
 *
 * NIO服务端示例
 */
public class PlainNioEchoServer {
    public void serve (int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        // 创建一个服务端的Channel（通信信道），设置为非阻塞模式，并绑定到一个Socket对象（即InetSocketAddress对象），
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverChannel.bind(address);
        serverChannel.configureBlocking(false);

        // 创建一个选择器
        Selector selector = Selector.open();

        // 将之前创建的Channel注册到选择器上，并注明该Channel感兴趣的事情为SelectionKey.OP_ACCEPT
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            try {
                // block until something is selected
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                // handle in a proper way
                break;
            }
            // get all SelectionKey instances
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // remove the SelectionKey from the iterator
                iterator.remove();
                try {
                    // accept the client connection
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        // 接受客户端的请求
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        // register connection to selector and set ByteBuffer
                        client.register(selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ, ByteBuffer.allocate(100));
                    }
                    // check for SelectionKey for read
                    if (key.isReadable()) { // 客户端channel是否可读，如果可读，则读取客户端发送的数据，即将数据从channel读取到ByteBuffer中
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        // read data from channel to ByteBuffer
                        client.read(buffer);
                    }
                    // check for SelectionKey for write
                    if (key.isWritable()) { // 客户端channel是否可写，如果可写，则可以向客户端发送数据，即将数据从ByteBuffer写入到channel中
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        // change from read mode to write mode
                        buffer.flip();
                        // write data from ByteBuffer to channel
                        client.write(buffer);
                        buffer.compact();
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                        // do something
                    }
                }
            }
        }
    }
}
