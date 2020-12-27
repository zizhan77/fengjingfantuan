package com.mem.vo.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: com.mem.vo.common.util
 * User: baiyuehui
 * Date: 2019/7/3
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XStreamCDATA {

}