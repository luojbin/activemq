package com.jitai.amqDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * student 对应实体类
 *
 * @author luoyanfu
 * @version 2.0
 * @time 2018-05-31 14:22:33
 */ 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	private String id;

	private String name;

	private Integer age;

	private Integer sex;

}
