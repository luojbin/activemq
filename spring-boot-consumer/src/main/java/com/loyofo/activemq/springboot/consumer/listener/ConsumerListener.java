package com.loyofo.activemq.springboot.consumer.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {

    @JmsListener(destination="strQueue")
    private void strQueueListener(String message) {
        System.out.println("strQueue 监听器收到消息...." + message);
    }

    @JmsListener(destination="mapQueue")
    private void mapQueueListener(Message<?> message) {
        System.out.println("mapQueue 监听器收到消息...." + message.getPayload());
        System.out.println("mapQueue 监听器收到消息...." + message.getHeaders());
    }

    @JmsListener(destination="reply")
    private void replyListener(Message<?> message) {
        System.out.println("█ reply 监听器收到回复...." + message.getPayload());
        System.out.println("█ reply 监听器收到回复...." + message.getHeaders());
    }

    @JmsListener(destination="replyQueue")
    private String replyQueueListener(Message<?> message) {
        System.out.println("replyQueue 监听器收到消息...." + message.getPayload());
        System.out.println("replyQueue 监听器收到消息...." + message.getHeaders());
        return "收到消息, 转发到指定目的地" + message.getPayload();
    }

    @JmsListener(destination="retry")
    private void retry(Message<?> message) {
        System.out.println("█ retry 监听器收到尝试...." + message.getPayload());
        System.out.println("█ retry 监听器收到尝试...." + message.getHeaders());
        throw new RuntimeException();
    }

    @JmsListener(destination="ActiveMQ.DLQ")
    private void dlq(Message<?> message) {
        System.out.println("死信队列...." + message.getPayload());
        System.out.println("死信队列...." + message.getHeaders());
    }

    @JmsListener(destination="exception")
    private void exception(Message<?> message) {
        System.out.println("异常队列, 不应该收到消息...." + message.getPayload());
        System.out.println("异常队列, 不应该收到消息...." + message.getHeaders());
    }
}
