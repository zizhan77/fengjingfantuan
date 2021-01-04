package com.mem.vo.common.constant;

public enum StatusEnum {
    NO(0, "待审核"),
    ON(1, "审核通过"),
    OFF(2, "审核未通过");

    StatusEnum(Integer code, String name) {
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
