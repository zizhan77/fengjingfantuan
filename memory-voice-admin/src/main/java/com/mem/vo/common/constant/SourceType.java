package com.mem.vo.common.constant;

/**
 * @author litongwei
 * @description: 来源系统
 * @date 2019/5/30 9:52
 */
public enum SourceType {

    WX_MINI("WX_MINI", "微信小程序端", "openId"),
    PC_ADMIN("PC_ADMIN", "平台运营端", "phoneNumber"),
    SPONSOR("SPONSOR", "赞助商端", "phoneNumber"),
    SPONSOR_EXCHANGE("SPONSOR_EXCHANGE", "赞助商兑换端", "phoneNumber"),
    ORGENIZER("ORGENIZER","主办方端","username"),
    CHECKMAN("CHECKMAN", "验票员端", "phoneNumber");


    //端编码
    private String code;
    //描述信息
    private String description;
    //用于标识这一端的登录的用户业务主键
    private String businessCode;

    SourceType(String code, String description, String businessCode) {
        this.code = code;
        this.description = description;
        this.businessCode = businessCode;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
}
