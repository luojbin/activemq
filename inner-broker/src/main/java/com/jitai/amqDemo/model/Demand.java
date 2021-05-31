package com.jitai.amqDemo.model;

import com.jitai.amqDemo.common.annotation.IgnoreFieldAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * DEMAND 对应实体类
 *
 * @author luoyanfu
 * @version 2.0
 * @time 2018-05-31 16:00:59
 */ 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Demand  implements Serializable{

	@IgnoreFieldAnnotation
	private static final long serialVersionUID = 7966157858794709646L;

	@Id
	private String id;

	private String customer;

	private String input;

	private String output;

}
