package com.linyp.study.crawler.chapter_2.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by linyp on 2017/8/1.
 */
@ClientEndpoint
public class WebSocketClient {
    protected WebSocketContainer container;
    protected Session userSession = null;

    public WebSocketClient() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String server) {
        try {
            userSession = container.connectToServer(this, new URI(server));
        } catch (DeploymentException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) throws IOException {
        userSession.getBasicRemote().sendText(msg);
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Closed");
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        System.out.println(msg);
    }

    public void disConnect() throws IOException {
        userSession.close();
    }
}
