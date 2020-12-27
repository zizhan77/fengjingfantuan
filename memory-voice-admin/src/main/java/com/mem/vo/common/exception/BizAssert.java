package com.mem.vo.common.exception;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 业务断言，增加类型
 */
public abstract class BizAssert {

    public static void notEmpty(Collection<?> coll, String message) {
        if (CollectionUtils.isEmpty(coll)) {
            throw new BizException(message);
        }
    }


    public static void notEmpty(Collection<?> coll, String message, Integer exceptionType) {
        if (CollectionUtils.isEmpty(coll)) {
            throw new BizException(message, exceptionType);
        }
    }


    public static void isBlank(String text, String message) {
        if (StringUtils.isNotBlank(text)) {
            throw new BizException(message);
        }
    }


    public static void isBlank(String text, String message, Integer exceptionType) {
        if (StringUtils.isNotBlank(text)) {
            throw new BizException(message, exceptionType);
        }
    }


    public static void hasText(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new BizException(message);
        }
    }


    public static void hasText(String text, String message, Integer exceptionType) {
        if (StringUtils.isBlank(text)) {
            throw new BizException(message, exceptionType);
        }
    }


    public static void isTrue(boolean expression, String message, Integer exceptionType) {
        if (!expression) {
            throw new BizException(message, exceptionType);
        }
    }

    public static void isTrue(boolean expression, String message) {
        isTrue(expression, message, -1);
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void isNull(Object object, String message, Integer exceptionType) {
        if (object != null) {
            throw new BizException(message, exceptionType);
        }
    }

    public static void isNull(Object object, String message) {
        isNull(object, message, -1);
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void notNull(Object object, String message, Integer exceptionType) {
        if (object == null) {
            throw new BizException(message, exceptionType);
        }
    }

    public static void notNull(Object object, String message) {
        notNull(object, message, -1);
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void hasLength(String text, String message, Integer exceptionType) {
        if (!StringUtils.isEmpty(text)) {
            throw new BizException(message, exceptionType);
        }
    }

    public static void hasLength(String text, String message) {
        hasLength(text, message, -1);
    }

    public static void hasLength(String text) {
        hasLength(text,
            "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }


    public static void isInstanceOf(Class clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    public static void isInstanceOf(Class type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message +
                "Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
                "] must be an instance of " + type);
        }
    }

    public static void isAssignable(Class superType, Class subType) {
        isAssignable(superType, subType, "");
    }

    public static void isAssignable(Class superType, Class subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    public static void state(boolean expression, String message, Integer exceptionType) {
        if (!expression) {
            throw new BizException(message, exceptionType);
        }
    }

    public static void state(boolean expression, String message) {
        state(expression, message, -1);
    }

    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

}
