package com.mem.vo.common.constant;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 13:58
 */
public class RedisConstant {

    //缓存失效时间，单位为秒
    //1分钟
    public static final int EXPIRED_TIME_1M = 60;
    //5分钟
    public static final int EXPIRED_TIME_5M = 5 * 60;
    //10分钟
    public static final int EXPIRED_TIME_10M = 10 * 60;
    //1小时
    public static final int EXPIRED_TIME_1H = 60 * 60;
    //2小时
    public static final int EXPIRED_TIME_2H = 60 * 60 * 2;

    //6小时
    public static final int EXPIRED_TIME_6H = 6 * 60 * 60;
    //1天
    public static final int EXPIRED_TIME_1D = 24 * 60 * 60;
    //7天
    public static final int EXPIRED_TIME_7D = 7 * 24 * 60 * 60;
    //15天
    public static final int EXPIRED_TIME_15D = 15 * 24 * 60 * 60;
}
