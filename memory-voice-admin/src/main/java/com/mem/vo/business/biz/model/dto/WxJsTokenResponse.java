package com.mem.vo.business.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
 *
 * @author baiyuehui
 * @description: 获取js access_token
 * @date 2019/5/25 10:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxJsTokenResponse {

    //jsapi appid
    String appId;
    /**
     * 唯一标识
     */
    String access_token;
    /**
     * jsapi凭证  jsapi_ticket
     */
    String ticket;

    /**
     * 过期时间
     */
    Integer expires_in;

    /**
     * 错误码
     */
    String errcode;

    /**
     * 错误信息
     */
    String errmsg;

    String signature;

    String noncestr = System.currentTimeMillis()+"1";

    String timestamp;

/*    -1	系统繁忙，此时请开发者稍候再试
0	请求成功
40001	AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性
40002	请确保grant_type字段值为client_credential
40164	调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置。（小程序及小游戏调用不要求IP地址在白名单内。）*/

}
