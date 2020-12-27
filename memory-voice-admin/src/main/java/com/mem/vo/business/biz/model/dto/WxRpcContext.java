package com.mem.vo.business.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/auth.code2Session.html
 *
 * @author litongwei
 * @description: 微信登录验证返回的实体
 * @date 2019/5/25 10:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxRpcContext {

    /**
     * 用户唯一标识
     */
    String openid;

    /**
     * 会话密钥
     */
    String session_key;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html
     */
    String unionid;

    /**
     * 错误码
     */
    String errcode;

    /**
     * 错误信息
     */
    String errmsg;


}
