package com.loyofo.activemq.springboot.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;


@RequestMapping("jt")
@RestController
public class JtConsumer {

    @Autowired
    private JmsTemplate template;

    @GetMapping("get")
    public String get() throws JMSException {
        Message message = template.receive("demoQueue");
        if (message instanceof TextMessage) {
            String text = ((TextMessage) message).getText();
            System.out.println("JmsTemplate 获取到消息头ID " + message.getJMSMessageID());
            System.out.println("JmsTemplate 获取到消息内容 " + text);
            return text;
        }
        return "获取消息错误";
    }

    @GetMapping("getMsg")
    public String getMsg() {
        Object message = template.receiveAndConvert("demoQueue");
        System.out.println("JmsTemplate 获取到消息内容 " + message);
        return (String) message;
    }
}
