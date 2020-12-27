package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 16:53
 */
@Getter
@AllArgsConstructor
public enum  SmsEnum {
    PHONE_NUMBERS("PhoneNumbers","电话号码，多个电话号码用逗号隔开"),
    TEMPLATE_CODE("TemplateCode","短信模板编号"),
    SIGN_NAME("SignName","签名"),
    TEMPLATE_PARAM("TemplateParam", "模板参数"),
    CODE("code","验证模板中的验证码对应Key"),
    SUCCESS_CODE("ok","返回成功Code"),
    FIELD_CODE("code","json替换使用");

    private String code;
    private String name;
}
