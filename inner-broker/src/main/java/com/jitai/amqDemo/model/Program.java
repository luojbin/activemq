package com.jitai.amqDemo.model;

import com.jitai.amqDemo.common.annotation.IgnoreFieldAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * PROGRAM 对应实体类
 *
 * @author luoyanfu
 * @version 2.0
 * @time 2018-05-31 16:01:15
 */ 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program implements Serializable{
	@IgnoreFieldAnnotation
	private static final long serialVersionUID = 7846391446317828801L;

	@Id
	private String id;

	private String demandId;

	private String input;

	private String output;

	private String step1;

	private String step2;

	private String step3;

}
