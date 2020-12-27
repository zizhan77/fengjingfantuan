package com.mem.vo.common.constant;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/30 10:31
 */
public enum LoginStatus {

    UNLOGIN(1, "未登录"),
    SUCCESSFUL(2, "登录成功"),
    LOGIN_NOT_AUTH(3, "登录未授权");

    private Integer Code;
    private String desc;

    LoginStatus(Integer code, String desc) {
        Code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
