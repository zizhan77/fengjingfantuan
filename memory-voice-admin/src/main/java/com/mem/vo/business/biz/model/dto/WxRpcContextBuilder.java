package com.mem.vo.business.biz.model.dto;

import lombok.Data;

@Data
public class WxRpcContextBuilder {
    private String openid;

    private String session_key;

    private String unionid;

    private String errcode;

    private String errmsg;
}
