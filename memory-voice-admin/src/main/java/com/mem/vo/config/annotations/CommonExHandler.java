package com.mem.vo.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author litongwei
 * @description   通用异常处理注解
 * @date 2019/6/26 15:18
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface CommonExHandler {

    String key() default ""; //异常日志描述前缀
}
