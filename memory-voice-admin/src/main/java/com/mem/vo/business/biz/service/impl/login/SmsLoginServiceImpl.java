package com.mem.vo.business.biz.service.impl.login;

import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.login.SmsLoginService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.LoginStatus;
import com.mem.vo.common.constant.RedisPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 19:00
 */
@Service("smsLoginService")
@Slf4j
public class SmsLoginServiceImpl implements SmsLoginService {

    @Resource
    private TokenService tokenService;

    @Override
    public String getToken(User user) {
        CommonLoginContext commonLoginContext =
                CommonLoginContext.builder()
                        .sourceCode(user.getSourceName())
                        .bizCode(user.getPhoneNumber())
                        .status(LoginStatus.SUCCESSFUL.getCode())
                        .userId(user.getId()).build();

        return tokenService.getTokenByContext(commonLoginContext, RedisPrefix.CHECK_MAN_TOKEN.getCode());
    }

    @Override
    public String getTokenFifteenDay(User user) {
        CommonLoginContext commonLoginContext =
                CommonLoginContext.builder()
                        .sourceCode(user.getSourceName())
                        .bizCode(user.getPhoneNumber())
                        .status(LoginStatus.SUCCESSFUL.getCode())
                        .userId(user.getId()).build();

        return tokenService.getTokenFifteenDay(commonLoginContext, RedisPrefix.SPONSOR_EXCHANGE_TOKEN.getCode());
    }

}
