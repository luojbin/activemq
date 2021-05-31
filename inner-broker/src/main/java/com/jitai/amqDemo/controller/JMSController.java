package com.jitai.amqDemo.controller;

import com.jitai.amqDemo.model.Demand;
import com.jitai.amqDemo.service.MqService;
import com.jitai.amqDemo.service.StudentService;
import com.jitai.amqDemo.util.ResultUtil;
import com.jitai.amqDemo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luojbin
 * @version 1.0
 * @create 2018/4/12 10:47
 */
@RequestMapping("/jms")
@Controller
public class JMSController {

    @Autowired
    MqService mqService;
    
    @Autowired
    private StudentService studentService;


    /////////////////////// 基本功能测试 /////////////////////
    
    /**
     * jms 发送消息
     */
    @RequestMapping("/send")
    @ResponseBody
    public ResultUtil sendController(HttpServletRequest request, HttpServletResponse response){
    	String message = request.getParameter("message");
    	String destinationName= request.getParameter("destinationName");
    	String destinationType = request.getParameter("destinationType");
    	mqService.sendMessage(message, destinationName, destinationType);
        return ResultUtil.ok().set("msg", "发送成功");
    }
    
    /**
     * jms 获取消息
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResultUtil getController(HttpServletRequest request, HttpServletResponse response){
    	String destinationName= request.getParameter("destinationName");
    	String destinationType = request.getParameter("destinationType");
    	String message =  mqService.getMessage(destinationName, destinationType);
        return ResultUtil.ok().set("msg", message);
    }
    
    /**
     * 通过 jms, 异步保存一个 Student
     */
    @RequestMapping("/stu_jms")
    @ResponseBody
    public ResultUtil saveStudent(HttpServletRequest request, HttpServletResponse response){
    	String name = request.getParameter("name");
    	String age= request.getParameter("age");
    	String sex = request.getParameter("sex");
    	
    	Map<String, String> param = new HashMap<>();
    	param.put("name", name);
    	param.put("age", age);
    	param.put("sex", sex);
    	
	    try{
	    	// 发送消息
	    	mqService.saveStudent(param);
	    	return ResultUtil.ok().set("msg", "stu_jms发送成功");
		}catch(Exception e){
			return ResultUtil.fail().set("msg", "stu_jms发送失败");
		}
    }

    /**
     * 通过 controller-service-dao 方式同步保存学生信息
     */
    @RequestMapping("/stu_csd")
    @ResponseBody
    public ResultUtil insert(HttpServletRequest request, HttpServletResponse response){
    	String name = request.getParameter("name");
    	String age= request.getParameter("age");
    	String sex = request.getParameter("sex");
    	
    	Map<String, String> param = new HashMap<>();
    	param.put("name", name);
    	param.put("age", age);
    	param.put("sex", sex);
    	
    	try{
    		// 直接调用service
    		studentService.save(param);
    		return ResultUtil.ok().set("msg", "插入student成功");
    	}catch(Exception e){
    		return ResultUtil.fail().set("msg", "插入student失败");
    	}
    }
    
    /////////////////////// 队列任务测试 /////////////////////
    /**
     * 只接受  demand 请求, 剩下的操作根据消息监听器去控制
     * @see com.jitai.amqDemo.jms.MessageListener
     */
    @RequestMapping("/demand")
    @ResponseBody
    public ResultUtil demandController(HttpServletRequest request, HttpServletResponse response){
    	String id =  request.getParameter("id");
    	String customer= request.getParameter("customer");
    	String input = request.getParameter("input");
    	String output =  request.getParameter("output");
    	
    	Demand demand = new Demand();
    	if(StringUtil.isNotEmpty(id)){
    		demand.setId(id);
    	}
    	demand.setCustomer(customer);
    	demand.setInput(input);
    	demand.setOutput(output);

    	String msg = null; 
    	/* 
    	 * 未指定 id, 走完整开发流程, 不设置 replyTo
    	 * 指定id=2, 设置replyTo
    	 */
    	if("2".equals(demand.getId())){
    		msg = mqService.sendObjectWithReply("demand", demand);
    	} else {
    		msg = mqService.sendObject("demand", demand);
    	}
        return ResultUtil.ok().set("msg", msg);
    }
}
