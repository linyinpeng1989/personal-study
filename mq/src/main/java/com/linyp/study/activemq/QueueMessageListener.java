package com.linyp.study.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by linyp on 2017/4/11.
 */
public class QueueMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("QueueMessageListener监听到文本消息："+tm.getText());
            // do something
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
