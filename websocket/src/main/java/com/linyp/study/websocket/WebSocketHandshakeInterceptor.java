package com.linyp.study.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by linyp on 2016/8/29.
 *
 * 拦截器，
 */
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 获取请求中的相关信息，并设置到当前的WebSocketHandler中
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = servletServerRequest.getServletRequest();
            //Long doctorUserId = (Long) httpServletRequest.getAttribute("doctorUserId");
            //String doctorUserName = (String) httpServletRequest.getAttribute("doctorUserName");
            Long doctorUserId = Long.valueOf(httpServletRequest.getParameter("doctorUserId"));
            String doctorUserName = httpServletRequest.getParameter("doctorUserName");
            attributes.put("doctorUserId", doctorUserId);
            attributes.put("doctorUserName", doctorUserName);
        }
        //return super.beforeHandshake(request, response, wsHandler, attributes);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        //super.afterHandshake(request, response, wsHandler, ex);
    }
}
