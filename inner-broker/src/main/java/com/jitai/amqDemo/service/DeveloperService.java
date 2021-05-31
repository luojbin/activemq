package com.jitai.amqDemo.service;

import com.jitai.amqDemo.dao.DemandDao;
import com.jitai.amqDemo.model.Demand;
import com.jitai.amqDemo.model.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author luojbin
 * @version 1.0
 * @create 2018/4/17 17:06
 */
@Service
public class DeveloperService {

	@Autowired
	private JmsTemplate jmsTemplate;


	@Autowired
	private DemandDao demandDao;

	private static Logger logger = LoggerFactory.getLogger(DeveloperService.class);

	public void develop(String demandId) {
		try {
			Demand demand = demandDao.getById(demandId);
			Program program = new Program();

			System.out.println("紧张的开发中...");
			Thread.sleep(10000);

			program.setDemandId(demandId);
			program.setStep1("  步骤一, 打开电脑...");
			program.setStep2("  步骤二, 执行程序...");
			program.setStep3("  步骤三, 得到结果...");
			// 故意留下错误
			program.setInput(demand.getInput());
			program.setOutput(demand.getInput());

			// 完成开发计划, 发信到 examine 队列
			jmsTemplate.convertAndSend("examine", program);
			System.out.println("开发完成, 让产品验收...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fix(Program program) {
		try {
			String demandId = program.getDemandId();
			Demand demand = demandDao.getById(demandId);

			System.out.println("紧张的改 bug 中...");
			Thread.sleep(5000);

			Program fixed = program;
			// 修正前面留下的错误
			fixed.setOutput(demand.getOutput());

			// 完成改 bug 任务, 发信到 examine 队列
			jmsTemplate.convertAndSend("examine", fixed);
			System.out.println("改bug完成, 让产品验收...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
