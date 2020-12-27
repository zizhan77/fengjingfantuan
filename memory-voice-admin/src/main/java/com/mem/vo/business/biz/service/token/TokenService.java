package com.mem.vo.business.biz.service.token;

import com.mem.vo.business.biz.model.dto.CommonLoginContext;

/**
 * @author litongwei
 * @description: token 管理
 * @date 2019/5/25 12:21
 */
public interface TokenService {

    /**
     * 根据登录上下文信息取 token
     */
    String getTokenByContext(CommonLoginContext loginContext, String preFix);

    /**
     * 根据 token获取登录信息
     */
    CommonLoginContext getContextByken(String token);

    /**
     * 延长token 失效时间
     */
    void prolongTime(String token);

    boolean updateTokenContext(String token,CommonLoginContext loginContext);

    boolean isTokenEffective(String token, String prefix);

    /**
     * 修改密码，删除token
     * @return
     */
    boolean delteToken(String token);


    boolean delteTokenByUserId(Long userId);

    /**
     * 根据登录上下文信息取 token 15天后过期，不每次登录更新
     */
    String getTokenFifteenDay(CommonLoginContext loginContext, String preFix);

    String getSponsorToken(CommonLoginContext loginContext);
}
