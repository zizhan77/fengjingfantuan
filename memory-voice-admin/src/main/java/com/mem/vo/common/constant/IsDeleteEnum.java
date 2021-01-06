package com.mem.vo.common.constant;

public enum IsDeleteEnum {
    YES(Integer.valueOf(0), "未删除"),
    NO(Integer.valueOf(1), "已删除");

    IsDeleteEnum(Integer code, String name) {
    this.code = code;
    this.name = name;
    }

    private Integer code;

    private String name;

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
