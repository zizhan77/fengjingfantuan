package com.mem.vo.common.constant;

public enum IsShare {
    NO(0, "没有分享过"),
    IS(1, "已经分享过");

    IsShare(Integer Code, String name) {
        this.Code = Code;
        this.name = name;
    }

    private Integer Code;

    private String name;

    public Integer getCode() {
        return this.Code;
    }

    public String getName() {
        return this.name;
    }
}
