package com.linyp.study.netty.websocket;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.RandomAccessFile;

/**
 * Created by Lin on 2016/5/25.
 *
 * WebSocket，处理http请求
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    // websocket标识
    private final String wsUri;

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        // 如果是websocket请求，请求地址uri等于wsuri
        if (wsUri.equalsIgnoreCase(fullHttpRequest.getUri())) {
            // 递增引用计数器（保留）并且将它传递给在 ChannelPipeline 中的下个 ChannelInboundHandler
            channelHandlerContext.fireChannelRead(fullHttpRequest.retain());
        }
        // 如果不是websocket请求
        else {
            // 处理符合 HTTP 1.1的 “100 Continue” 请求
            if (HttpHeaders.is100ContinueExpected(fullHttpRequest)) {
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
                channelHandlerContext.writeAndFlush(response);
            }
            //获取index.html的内容响应给客户端
            RandomAccessFile file = new RandomAccessFile(System.getProperty("user.dir") + "/index.html", "r");
            HttpResponse response = new DefaultHttpResponse(fullHttpRequest.getProtocolVersion(), HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/html; charset=UTF-8");
            boolean keepAlive = HttpHeaders.isKeepAlive(fullHttpRequest);
            //如果http请求保持活跃，设置http请求头部信息并响应请求
            if (keepAlive) {
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
            }
            channelHandlerContext.write(response);
            //如果不是https请求，将index.html内容写入通道
            if (channelHandlerContext.pipeline().get(SslHandler.class) == null) {
                channelHandlerContext.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                channelHandlerContext.write(new ChunkedNioFile(file.getChannel()));
            }
            //标识响应内容结束并刷新通道
            ChannelFuture future = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive) {
                //如果http请求不活跃，关闭http连接
                future.addListener(ChannelFutureListener.CLOSE);
            }
            file.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
