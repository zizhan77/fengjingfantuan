package com.mem.vo.common.dto;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 10:01
 */
public enum ResultCode {
    SUCCESS(200, "success"), // 正确请求
    ERROR(500, "failure"); // 请求错误

    /**
     * 主键
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    ResultCode(final Integer code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}