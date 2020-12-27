package com.mem.vo.business.biz.model.dto;

import lombok.Data;

/**
 * @author litongwei
 * @description: 微信登录验证请求参数
 * @date 2019/5/25 10:13
 */
@Data
public class WxLoginRequest {

    /**
     * js appId
     */
    private String jsAppid;

    /**
     * js appSecret
     */
    private String jsAppSecret;
    /**
     * 小程序 appId
     */
    private String appid;
    /**
     * 小程序 appSecret
     */
    private String appSecret;
    /**
     * 授权类型，此处只需填写 authorization_code
     */
    private String grantType;

}
