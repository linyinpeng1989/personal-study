package com.linyp.study.netty.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Lin on 2016/5/26.
 *
 * NIO2服务端示例
 */
public class PlainNio2EchoServer {
    public void  serve (int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        // bind server to port
        serverChannel.bind(address);
        // 新建数量为1的闭锁
        final CountDownLatch latch = new CountDownLatch(1);
        // start to accept new client connections. Once one is accepted the CompletionHandler will get called.
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(final AsynchronousSocketChannel channel, Object attachment) {
                // again accept new client connections
                serverChannel.accept(null,this);
                ByteBuffer buffer = ByteBuffer.allocate(100);
                // trigger a read operation on the channel, the given CompletionHandler will be notified
                // once something was read
                channel.read(buffer,buffer,new EchoCompletionHandler(channel));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                try {
                    // close the socket on error
                    serverChannel.close();
                } catch (IOException ex) {
                    // ignore on close
                } finally {
                    latch.countDown();
                }
            }
        });
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    private final class EchoCompletionHandler implements CompletionHandler<Integer,ByteBuffer> {
        private final AsynchronousSocketChannel channel;

        public EchoCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, final ByteBuffer buffer) {
            // change from read mode to write mode
            buffer.flip();
            // trigger a write operation on the channel, the given CompletionHandler will be notified
            // once something was written
            channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    if (buffer.hasRemaining()) {
                        // trigger again a write operation if something is left in the ByteBuffer
                        channel.write(buffer,buffer,this);
                    } else {
                        // trigger a read operation on the channel, the given CompletionHandler will be notified
                        // once something was read
                        buffer.compact();
                        channel.read(buffer,buffer,EchoCompletionHandler.this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException ex) {
                        // ignore no close
                    }
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer buffer) {
            try {
                channel.close();
            } catch (IOException ex) {
                // ignore no close
            }
        }
    }
}
