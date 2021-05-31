package com.jitai.amqDemo.service;

import com.jitai.amqDemo.dao.StudentDao;
import com.jitai.amqDemo.model.Student;
import com.jitai.amqDemo.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author luojbin
 * @version 1.0
 * @create 2018/4/17 17:06
 */
@Service
public class StudentService {
	@Autowired
	private StudentDao studentDao;

	@Autowired
	private JmsTemplate jmsTemplate;

	private static Logger logger = LoggerFactory.getLogger(StudentService.class);

	public int save(Map<String, String> param) throws Exception{
		String name = param.get("name");
		String age = param.get("age");
		String sex = param.get("sex");

		Student student = new Student();
		student.setId(StringUtil.getUUID32());
		student.setAge(Integer.valueOf(age));
		student.setSex(Integer.valueOf(sex));
		student.setName(name);
			int n = studentDao.save(student);
			
			jmsTemplate.convertAndSend("result", "保存成功, 发送成功");

			if("1".equals(sex)){
				System.out.println("抛RuntimeException");
				throw new RuntimeException("不给过");
			}
			if("2".equals(sex)){
				System.out.println("抛Exception");
				throw new Exception("不给过");
			}
			if("3".equals(sex)){
				System.out.println("抛ClassNotFoundException");
				throw new ClassNotFoundException("不给过");
			}
			return n;
	}

	public int edit(Map<String, String> param) {
		String id = param.get("id");
		String name = param.get("name");
		String age = param.get("age");
		String sex = param.get("sex");

		Student student = new Student();
		student.setId(id);
		student.setAge(Integer.valueOf(age));
		student.setSex(Integer.valueOf(sex));
		student.setName(name);
		try {
			return studentDao.update(student);
		} catch (Exception e) {
			logger.error("edit student error {}", e);
			throw new RuntimeException("edit student error");
		}
	}
}
