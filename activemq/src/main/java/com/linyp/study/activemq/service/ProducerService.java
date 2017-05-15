package com.linyp.study.activemq.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * Created by linyp on 2017/4/12.
 * 消息生产者
 */
@Service
public class ProducerService {
    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;
    @Resource(name = "demoQueueDestination")
    private Destination destination;

    public void sendMessage(final String msg) {
        System.out.println(Thread.currentThread().getName() + " 向队列(" + destination.toString() + ")发送消息---------> " + msg);
        jmsTemplate.send(destination, session -> session.createTextMessage(msg));
    }
}
