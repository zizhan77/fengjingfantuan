package com.mem.vo.common.util;

import org.springframework.beans.BeanUtils;

/**
 * Created by litongwei3 on 2018/4/23.
 */
public class BeanCopyUtil {

    /**
     * 不同类型对象数据copy
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        T target = BeanUtils.instantiate(targetClass);
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 不同类型对象数据copy 可指定忽略属性
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass,String ignoreProper) {
        T target = BeanUtils.instantiate(targetClass);
        BeanUtils.copyProperties(source, target,ignoreProper);
        return target;
    }

    /**
     * 对象复制
     *
     * @param source
     * @return
     */
    public static Object copyBeans(Object source) {
        Object target = BeanUtils.instantiate(source.getClass());
        BeanUtils.copyProperties(source, target);
        return target;
    }

}
