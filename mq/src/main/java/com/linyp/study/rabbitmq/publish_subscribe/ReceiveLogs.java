package com.linyp.study.rabbitmq.publish_subscribe;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by linyp on 2017/5/18.
 */
public class ReceiveLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.198.130");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明exchange名字及类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 创建一个非持久化的、独有的并且是自动删除的已命名的queue（临时队列）
        String queueName = channel.queueDeclare().getQueue();
        // 将队列绑定到exchange,routingKey默认为空
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
