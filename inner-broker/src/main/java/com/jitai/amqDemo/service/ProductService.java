package com.jitai.amqDemo.service;

import com.jitai.amqDemo.dao.DemandDao;
import com.jitai.amqDemo.dao.ExamineDao;
import com.jitai.amqDemo.dao.ProgramDao;
import com.jitai.amqDemo.model.Demand;
import com.jitai.amqDemo.model.Examine;
import com.jitai.amqDemo.model.Program;
import com.jitai.amqDemo.util.DateUtils;
import com.jitai.amqDemo.util.StringUtil;
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
public class ProductService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private DemandDao demandDao;
	@Autowired
	private ProgramDao programDao;
	@Autowired
	private ExamineDao examineDao;

	public String review(Demand demand) {
		System.out.print("产品经理拿到需求:");
		System.out.println(demand);
		if("1".equals(demand.getId())){
			return "这个产品已经做过了, 不用再做";
		}
		if("2".equals(demand.getId())){
			return "这个产品B组同学正在做";
		}
		try {
			String demandId = StringUtil.getUUID32();
			demand.setId(demandId);
			demandDao.save(demand);

			System.out.println("需求没毛病, 开发开始干活吧...");
			jmsTemplate.convertAndSend("develop", demandId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void examine(Program program) {
		try {
			String demandId = program.getDemandId();
			String programId = program.getId();
			Demand demand = demandDao.getById(demandId);
			String input = demand.getInput();
			String output = demand.getOutput();

			System.out.println("开始测试...");
			System.out.println("输入input: " + input);

			String step1 = program.getStep1();
			String step2 = program.getStep2();
			String step3 = program.getStep3();
			System.out.println(step1);
			System.out.println(step2);
			System.out.println(step3);

			Thread.sleep(1000);

			String programOut = program.getOutput();
			System.out.println("得到output: " + programOut);

			boolean isRight = programOut.equals(output) ? true : false;

			Examine exam = new Examine();
			exam.setId(StringUtil.getUUID32());
			exam.setDemandId(demandId);
			exam.setProgramId(programId);
			exam.setTestResult(isRight ? 1 : 0);
			exam.setTestDate(DateUtils.toStr());
			examineDao.save(exam);

			if (isRight) {
				System.out.println("测试通过, 没毛病, 开发流程结束");
				program.setId(StringUtil.getUUID32());
				programDao.save(program);
			} else {
				System.out.println("测试未通过, 返回给开发改bug");
				jmsTemplate.convertAndSend("fixme", program);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
