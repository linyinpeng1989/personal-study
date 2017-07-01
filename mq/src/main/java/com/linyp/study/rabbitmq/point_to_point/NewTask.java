package com.linyp.study.rabbitmq.point_to_point;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by linyp on 2017/5/16.
 */
public class NewTask {
    private static final String DURABLE_QUEUE_NAME = "durable_hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.198.130");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /*
            queue: 队列名
            durable: 是否持久化，即是否将队列持久化到磁盘或数据库
            exclusive: 是否排外，如果设置了排外为true的队列只可以在本次的连接中被访问，也就是说在当前连接创建多少个
                channel访问都没有关系，但如果是一个新的连接来访问则不可以。排外的queue在当前连接被断开的时候会自动
                消失（清除），无论是否设置了持久化
            autoDelete: 是否自动删除
            arguments:
         */
        // 队列持久化
        boolean durable = true;
        channel.queueDeclare(DURABLE_QUEUE_NAME, durable, false, false, null);

        //String message = "Hello World!";
        for (int index = 0; index < 30; index++) {
            String message = getMessage(args) + " NO." + index;
            // 发布消息：默认交换器（空字符串），指定路由键，消息持久化
            channel.basicPublish("", DURABLE_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

    public static String getMessage(String[] strs) {
        if (strs.length < 1)
            return "Hello World!";
        return joinStrings(strs, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
