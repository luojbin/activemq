package com.loyofo.activemq.boot.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jmt")
@RestController
public class JmtConsumer {

    @Autowired
    private JmsMessagingTemplate template;

    @GetMapping("get")
    public String get() {
        Message<?> message = template.receive("demoQueue");
        System.out.println("JmsMessagingTemplate 获取到消息头 "+ message.getHeaders());
        System.out.println("JmsMessagingTemplate 获取到消息内容 "+  message.getPayload());
        return message.getPayload().toString();
    }
    @GetMapping("getMsg")
    public String getMsg() {
        String message = template.receiveAndConvert("demoQueue", String.class);
        System.out.println("JmsMessagingTemplate 获取到消息内容 "+ message);
        return message;
    }
}
