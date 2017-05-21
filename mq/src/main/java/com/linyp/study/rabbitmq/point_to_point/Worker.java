package com.linyp.study.rabbitmq.point_to_point;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by linyp on 2017/5/18.
 */
public class Worker {
    private static final String DURABLE_QUEUE_NAME = "durable_hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.198.130");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 直到当前消费者发回ach之后，才会再接收消息（公平分发）
        channel.basicQos(1);

        // 持久化的队列
        boolean durable = true;
        channel.queueDeclare(DURABLE_QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                // 模拟耗时任务
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                    // 向队列发送回复消息，队列接收该回复消息后会删除队列中对应的消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // true表示自动接收，不需要消费端发送ack，队列会把已经发送的消息删除；
        // false表示需要消费端发送ack，队列接收到ach之后才会把对应的删除消息
        boolean autoAck = false;
        channel.basicConsume(DURABLE_QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork(String message) throws InterruptedException {
        for (char ch: message.toCharArray()) {
            if (ch == '.') Thread.sleep(20000);
        }
    }
}
