package com.mem.vo.common.constant;

public enum AppEnum {
    App_id("500699022", "App_id"),
    KEY("b79befeab99dcbeae840377298ecc05e", "key");

    AppEnum(String Code, String name) {
        this.Code = Code;
        this.name = name;
    }

    private String Code;

    private String name;

    public String getCode() {
        return this.Code;
    }

    public String getName() {
        return this.name;
    }
}
