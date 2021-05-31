package com.jitai.amqDemo.common.annotation;

import java.lang.annotation.*;

/**
 * 忽略非表字段
 *
 * @author zhangyi
 * @version 2.0
 * @time 2018年1月26日 上午11:17:26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) // 在运行期保留注解信息
@Documented // 在生成javac时显示该注解的信息
public @interface IgnoreFieldAnnotation {

}
