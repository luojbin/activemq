package com.jitai.amqDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * CANCEL 对应实体类
 *
 * @author luoyanfu
 * @version 2.0
 * @time 2018-05-31 16:06:39
 */ 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cancel {

	@Id
	private String id;

	private String customer;

	private String input;

	private String output;

	private String remark;

}
