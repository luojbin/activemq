package com.loyofo.activemq.boot.provider.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("jmt")
@RestController
public class JmtProvider {
    @Autowired
    private JmsMessagingTemplate messagingTemplate;

    @PostMapping("sendDemo")
    public String send(String message) {
        messagingTemplate.convertAndSend("demoQueue", message);
        return "手动消息 发送成功";
    }

    @PostMapping("sendStr")
    public String sendStr(String message) {
        messagingTemplate.convertAndSend("strQueue", message);
        return "str 发送成功";
    }
    @PostMapping("sendMap")
    public String sendMap(String message) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("str", message);
        map.put("integer", 123);
        map.put("boolean", false);
        messagingTemplate.convertAndSend("mapQueue", map);
        return "map 发送成功";
    }

    @PostMapping("sendMapAndReply")
    public String sendMapAndReply() {
        Map<String, Object> header = new HashMap<>();
        header.put("jms_replyTo", new ActiveMQQueue("reply"));
        messagingTemplate.convertAndSend("replyQueue", "来自 jmt 的消息", header);
        return "map 发送成功";
    }

    @PostMapping("sendDlq")
    public String sendDlq(String message) {
        messagingTemplate.convertAndSend("retry", message);
        return "jmt 消息重发与死信 发送成功";
    }

}
