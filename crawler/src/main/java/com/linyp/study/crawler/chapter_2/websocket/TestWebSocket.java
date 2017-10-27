package com.linyp.study.crawler.chapter_2.websocket;

import java.io.IOException;

/**
 * Created by linyp on 2017/8/1.
 */
public class TestWebSocket {
    public WebSocketClient wsc;

    public void start() throws InterruptedException, IOException {
        wsc = new WebSocketClient();
        wsc.connect("ws://localhost:9800/webSocket");
        Thread.sleep(1000);
        wsc.disConnect();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        TestWebSocket t = new TestWebSocket();
        t.start();
        Thread.sleep(1000);
    }
}
