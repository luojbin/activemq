package com.jitai.amqDemo.service;

import com.jitai.amqDemo.common.constant.ActiveMQConstant;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * @author luojbin
 * @version 1.0
 * @create 2018/4/17 17:06
 */
@Service
public class MqService {
	@Autowired
	private JmsTemplate jmsTemplate;

	/**
	 * 从指定目的地获取一条消息
	 * @param destinationName
	 * @param destinationType
	 * @return
	 */
	public String getMessage(String destinationName, String destinationType) {
		System.out.println("\n>>> MqService.getMessage入参: destinationName="+ destinationName + ", destinationType="+ destinationType);
		
		Destination destination = null;
		if (ActiveMQConstant.DESTNATION_QUEUE.equals(destinationType)) {
			destination = new ActiveMQQueue(destinationName);
		}
		if (ActiveMQConstant.DESTINATION_TOPIC.equals(destinationType)) {
			destination = new ActiveMQTopic(destinationName);
		}
		String msg = null;
		try {
			Message message = jmsTemplate.receive(destination);
			msg = ((TextMessage) message).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(">>> MqService.getMessage返回值: msg="+ msg);
		return msg;
	}

	/**
	 * 给指定的目的地发送一条消息
	 * @param message
	 * @param destinationName
	 * @param destinationType
	 */
	public void sendMessage(String message, String destinationName, String destinationType) {
		System.out.println("\n>>> MqService.sendMessage入参: destinationName="+ destinationName + ", destinationType="+ destinationType + ", message="+ message);
		Destination destination = null;
		if (ActiveMQConstant.DESTNATION_QUEUE.equals(destinationType)) {
			destination = new ActiveMQQueue(destinationName);
		}
		if (ActiveMQConstant.DESTINATION_TOPIC.equals(destinationType)) {
			destination = new ActiveMQTopic(destinationName);
		}
		jmsTemplate.convertAndSend(destination, message, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				Destination replyDest = new ActiveMQQueue("reply");
				message.setJMSReplyTo(replyDest);
				return message;
			}
		});
		System.out.println(">>> MqService.sendMessage 结束, 无返回值");
	}

	public void saveStudent(Map<String, String> param) {
		System.out.println("\n>>> MqService.saveStudent入参: param="+ param);
		jmsTemplate.convertAndSend("student", param);
		System.out.println(">>> MqService.saveStudent 结束, 无返回值");
	}
	
	public String sendObject(String destinationName, Object mesasge){
		System.out.println("\n>>> MqService.sendObject入参: destinationName="+ destinationName + ", message="+ mesasge);
		jmsTemplate.convertAndSend(destinationName, mesasge);
		System.out.println(">>> MqService.sendObject 结束");
		return "需求发送成功";
	}
	
	public String sendObjectWithReply(String destinationName, Object mesasge){
		System.out.println("\n>>> MqService.sendObjectWithReply入参: destinationName="+ destinationName + ", message="+ mesasge);
		jmsTemplate.convertAndSend(destinationName, mesasge, new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				Destination replyDest = new ActiveMQQueue("reply");
				message.setJMSReplyTo(replyDest);
				return message;
			}
		});
		System.out.println(">>> MqService.sendObjectWithReply 结束");
		return "需求发送成功";
	}

}
