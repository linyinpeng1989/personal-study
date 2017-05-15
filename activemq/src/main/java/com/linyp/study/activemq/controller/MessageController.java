package com.linyp.study.activemq.controller;

import com.linyp.study.activemq.controller.param.MessageParam;
import com.linyp.study.activemq.service.ConsumerService;
import com.linyp.study.activemq.service.ProducerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.TextMessage;

/**
 * Created by linyp on 2017/4/12.
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    /** 队列消息生产者 */
    @Resource(name = "producerService")
    private ProducerService producerService;
    /** 队列消息消费者 */
    @Resource(name = "consumerService")
    private ConsumerService consumerService;

    @RequestMapping(value = "/sendMessageOfPost", method = RequestMethod.POST)
    public void send(MessageParam param) {
        producerService.sendMessage(param.getMsg());
    }

    @RequestMapping(value = "/sendMessageOfGet")
    public void sendOfGet(String msg) {
        producerService.sendMessage(msg);
    }

    @RequestMapping(value = "/receiveMessage", method = RequestMethod.POST)
    public Object receive() {
        TextMessage textMessage = consumerService.receive();
        return textMessage;
    }
}
