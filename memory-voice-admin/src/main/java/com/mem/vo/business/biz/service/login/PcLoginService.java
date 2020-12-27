package com.mem.vo.business.biz.service.login;

import com.mem.vo.business.biz.model.dto.PcLoginRequest;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 12:08
 */


public interface PcLoginService<T> {

    T checkPasswd(PcLoginRequest pcLoginRequest);

    /**
     * 根据用户名密码 分配 token
     */
    String getToken(PcLoginRequest pcLoginRequest,T user);
}
