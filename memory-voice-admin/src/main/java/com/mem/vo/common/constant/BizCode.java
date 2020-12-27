package com.mem.vo.common.constant;

/**
 * 错误码
 */
public enum BizCode {
    SUCCESS(0, "成功"),
    INTERNAL_ERROR(500, "服务器内部错误"),
    PARAM_NULL(600,"参数为空"),
    FIELD_CHECK_ERROR(1001, "字段校验错误"),
    BIZ_1002(1002, "根据登录凭证返回登录信息失败"),
    BIZ_1003(1003, "根据登录凭证对象转换失败"),
    BIZ_1004(1004, "失效token，请重新登录"),
    BIZ_1005(1005, "当前手机号尚未注册，请先注册"),
    BIZ_1006(1006, "密码错误，请重试"),
    BIZ_1007(1007, "演出不存在"),
    BIZ_1008(1008, "地址不存在"),
    BIZ_1009(1009, "用户不存在"),
    BIZ_1010(1010, "解密失败,解密后的对象为空"),
    BIZ_1011(1011, "解密失败,手机号为空"),
    BIZ_1012(1012, "获取用户角色信息失败"),
    BIZ_1013(1013, "获取场地信息为空"),
    BIZ_1014(1014, "查询城市为空"),
    BIZ_1015(1015, "演员不存在"),
    BIZ_1016(1016, "选择票档为空"),
    BIZ_1017(1017, "选择场次为空"),
    BIZ_1018(1018, "登录未授权，请绑定手机号"),


    BIZ_1101(1101, "消息不存在"),
    BIZ_1102(1102, "该页列表为空"),
    BIZ_1103(1103, "项目不存在"),

    BIZ_1030(1030, "座位已被预占"),
    BIZ_1031(1031, "未更新座位状态"),
    BIZ_1032(1032, "座位不可变更"),
    BIZ_1033(1033, "没有该演出或票档库存信息"),
    BIZ_1034(1034, "座位已售出"),
    BIZ_1035(1035, "座位创建失败"),
    BIZ_1036(1036, "库存不足"),
    BIZ_1037(1037, "该行号已被创建"),

    BIZ_1050(1050, "没有该订单号"),
    BIZ_1051(1051, "订单状态异常"),
    BIZ_1052(1052, "兑换码创建订单异常"),
    ;

    private int code;
    private String message;

    BizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }}
