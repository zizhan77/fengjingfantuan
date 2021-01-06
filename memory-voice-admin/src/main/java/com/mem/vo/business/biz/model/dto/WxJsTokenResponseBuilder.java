package com.mem.vo.business.biz.model.dto;

import lombok.Data;

@Data
public class WxJsTokenResponseBuilder {
    private String appId;

    private String access_token;

    private String ticket;

    private Integer expires_in;

    private String errcode;

    private String errmsg;

    private String signature;

    private String noncestr;

    private String timestamp;
}
