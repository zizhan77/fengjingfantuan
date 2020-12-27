package com.mem.vo.business.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 18:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsLoginRequest {
    /**
     * 电话号号码
     */
    @NotBlank(message = "电话号码不能为空！")
    private String phoneNumber;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空！")
    private String verificationCode;

    /**
     * 登录端来源
     */
    @NotBlank(message = "登录端来源不能为空！")
    private String sourceName;

}
