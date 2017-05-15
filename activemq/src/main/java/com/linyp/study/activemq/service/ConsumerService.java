package com.linyp.study.activemq.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by linyp on 2017/4/12.
 * 消息消费者
 */
@Service
public class ConsumerService {
    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;
    @Resource(name = "demoQueueDestination")
    private Destination destination;

    public TextMessage receive() {
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列(" + destination.toString() + ")收到消息：\t" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage;
    }
}
