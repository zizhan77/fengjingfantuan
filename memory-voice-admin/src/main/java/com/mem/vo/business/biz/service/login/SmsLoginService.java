package com.mem.vo.business.biz.service.login;

import com.mem.vo.business.base.model.po.User;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 18:57
 */
public interface SmsLoginService {
    String getToken(User user);

    /**
     * 赞助商兑换端短信登录，缓存15天
     * @param user
     * @return
     */
    String getTokenFifteenDay(User user);
}
