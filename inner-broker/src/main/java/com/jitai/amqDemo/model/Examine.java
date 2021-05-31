package com.jitai.amqDemo.model;

import com.jitai.amqDemo.common.annotation.IgnoreFieldAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * EXAMINE 对应实体类
 *
 * @author luoyanfu
 * @version 2.0
 * @time 2018-05-31 16:01:25
 */ 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Examine implements Serializable{
	@IgnoreFieldAnnotation
	private static final long serialVersionUID = 3390590727332492311L;

	@Id
	private String id;

	private String demandId;

	private String programId;

	private String testDate;

	private Integer testResult;

}
