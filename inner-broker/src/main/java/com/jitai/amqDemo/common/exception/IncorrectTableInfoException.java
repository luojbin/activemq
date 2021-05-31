package com.jitai.amqDemo.common.exception;

/**
 * bean对应数据表不合法的相关异常
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月18日 下午2:00:17
 */
public class IncorrectTableInfoException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectTableInfoException(String msg) {
		super(msg);
	}

	public IncorrectTableInfoException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
