package com.mem.vo.common.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {


    /**
     * 将对象转换成Json字符串
     *
     * @param obj 需要转成Json的对象
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 将Json对象转换成对象
     */
    public static <T> T fromJson(String json, Class<T> obj) {
        return JSON.parseObject(json, obj);
    }
}
