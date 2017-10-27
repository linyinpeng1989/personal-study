package com.linyp.study.crawler.chapter_2.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by linyp on 2017/8/1.
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public WebSocketServer initWebSocketServer() {
        return new WebSocketServer();
    }

}
