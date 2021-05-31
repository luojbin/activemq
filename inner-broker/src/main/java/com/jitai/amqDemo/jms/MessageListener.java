package com.jitai.amqDemo.jms;

import com.jitai.amqDemo.model.Demand;
import com.jitai.amqDemo.model.Program;
import com.jitai.amqDemo.service.DeveloperService;
import com.jitai.amqDemo.service.ProductService;
import com.jitai.amqDemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageListener {
	
	@Autowired
	StudentService studentService;
	@Autowired
	DeveloperService developerService;
	@Autowired
	ProductService productService;
	
	/** 
	 * 事务控制流程测试
	 */
	@JmsListener(destination="student")
    public void studentListener(Map<String, String> param) throws Exception{
		System.out.println("\n>>> student 队列收到消息, 尝试保存...");
		int n = studentService.save(param);
		
		System.out.println("已保存"+ n +"个 student");
    }
	
	@JmsListener(destination="result")
    public void result(String msg){
		System.out.println("result 队列 收到消息:"+ msg);
    }
	
	/** 
	 * 消息队列异步处理demo: 模拟开发流程
	 * 业务方-提出需求:客户发消息到 demand 队列
	 * 产品-需求评审: 从 demand 获取需求, 通过的话保存到数据库, 并发给 develop 队列, 不通过发到 cancel 队列
	 * 开发者-开发: 从 develop 获取开发计划, 开发完成, 发送到 examine 队列
	 * 产品-验收: 从 examine 获取产品, 通过保存到数据库, 未通过返回给 fixme 队列,
	 * 
	 * @JmsListener 用来指定监听器要监听的队列/主题名
	 * @SendTo 
	 * 	如果该监听器有返回值, 则可以自动将消息发送到注解指定的目的地
	 * 	如果消息本身设置了 ReplyTo 的属性, 则会将返回值发送到消息指定的 ReplyTo 目的地
	 */
	
	@JmsListener(destination="demand")
	@SendTo("cancel")
    public String demandListener(Demand demand){
		System.out.println("\n>>> demand 监听器");
		String result = productService.review(demand);
		return result;
    }
	
	@JmsListener(destination="develop")
    public void developListener(String demandId){
		System.out.println("\n>>> develop 监听器");
		developerService.develop(demandId);
    }
	
	@JmsListener(destination="fixme")
    public void fixmeListener(Program program){
		System.out.println("\n>>> fixme 监听器");
		developerService.fix(program);
    }
	
	@JmsListener(destination="examine")
    public void examineListener(Program program){
		System.out.println("\n>>> examine 监听器");
		productService.examine(program);
    }
	
	@JmsListener(destination="cancel")
    public void cancelListener(String resultReason)  throws Exception{
		System.out.println("\n>>> cancel 监听器");
		System.out.println("取消原因: " + resultReason);
    }
	
	@JmsListener(destination="reply")
    public void replyListener(String reply)  throws Exception{
		System.out.println("\n>>> reply 监听器");
		System.out.println("返回消息: " + reply);
    }
	
	/**
	 * 虚拟主题
	 * 
	 * ActiveMQ 中, topic 只有在存在持久订阅者时, 才会将消息持久化
	 * 而持久订阅时, 每个持久订阅者, 都相当于有一个专属的 queue, 会收取所有的消息.
	 * 这会存在以下两个问题, 
	 * 1. 同一应用内的 consumer 负载均衡的问题, 
	 *    同一个应用上的一个持久订阅, 不能使用多个 consumer 来共同承担消息处理功能, 因为每个 consumer 都会收到所有消息.
	 *    queue模式可以解决这个问题, 但是broker 又不能将消息发送到多个应用端, 
	 *    JMS 规范中无法实现既要发布订阅, 又能让消费者分组
	 * 2. 同一应用内 consumer 端 failover 的问题
	 *    由于只能使用单个的持久订阅者, 如果这个订阅者出错挂掉, 则应用就无法处理消息了, 系统健壮性不高
	 * 
	 * 为了解决这两个问题, Activemq 提供了虚拟 topic 的功能, 使得:
	 * 
	 * 对发布者而言, 是一个 topic, 发布的消息可以被多个应用端使用, 主题名以"VirtualTopic."开头, 如"VirtualTopic.test1"
	 * 对某个应用端内的多个消费者而言, 是一个 queue, 多个消费者可以分工处理队列中的消息, 获取消息的队列名为:"Consumer.*.VirtualTopic.##", 如"Consumer.a.VirtualTopic.test1"
	 * 
	 * eg:
	 * 发布者发送主题消息, 目的地为"VirtualTopic.test1"
	 * 消费者接收队列消息, 目的地为"Consumer.a.VirtualTopic.test1" 和 "Consumer.B.VirtualTopic.test1"
	 */

	@JmsListener(destination="Consumer.a.VirtualTopic.test1")
    public void virtualTopicListener(String message)  throws Exception{
		System.out.println("\n>>> 虚拟主题消费者-A 收到消息: " + message);
    }
	
	@JmsListener(destination="Consumer.b.VirtualTopic.test1")
    public void virtualTopicListener2(String message)  throws Exception{
		System.out.println("\n>>> 虚拟主题消费者-B 收到消息: " + message);
    }
	
	
}
