package com.mem.vo.common.constant;

public enum  DefaultEnum {
    JIYIZHISHENG(0, "默认题库，记忆之声的ID");

    private String name;

    private Integer code;

    DefaultEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
