package com.mem.vo.business.biz.service.login;

import com.mem.vo.business.biz.model.dto.WxJsTokenResponse;
import com.mem.vo.business.biz.model.dto.WxRpcContext;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 10:38
 */

public interface WxLoginService {

    /**
     * 首次登录 jscode 凭证去微信验证
     */
    WxRpcContext getRpcContext(String jsCode);

    /**
     * 根据 wxRpcContext 获取 token
     */
    String getToken(WxRpcContext wxRpcContext,boolean isAuthorize,Long userId);


    WxJsTokenResponse getJsAccessToken();

    WxJsTokenResponse getWxSign(String url);


    /**
     * 商户server调用支付统一下单
     */
    //WxOrderContext getUnifiedOrder(WxOrderRequest wxOrderRequest)throws Exception;

}
