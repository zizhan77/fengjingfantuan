package com.mem.vo.common.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;


public class ValidateUtil {

    private static Validator validator;

    static {
        validator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory()
            .getValidator();
    }

    /**
     * 对指定对象进行验证
     *
     * @param o 要验证的对象
     * @return 返回验证后的字符串
     */
    public static String getValidateStr(Object o) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
        StringBuffer sb = new StringBuffer();
        for (ConstraintViolation violation : constraintViolations) {
            sb.append(violation.getMessage()).append(";");
        }
        return sb.toString();
    }

    /**
     * 对指定对象进行验证
     *
     * @param o 要验证的对象
     * @return 返回验证后的字符串
     */
    public static String getValidateStr(Object o, Class[] group) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o, group);
        StringBuffer sb = new StringBuffer();
        for (ConstraintViolation violation : constraintViolations) {
            sb.append(violation.getMessage()).append(";");
        }
        return sb.toString();
    }
}
