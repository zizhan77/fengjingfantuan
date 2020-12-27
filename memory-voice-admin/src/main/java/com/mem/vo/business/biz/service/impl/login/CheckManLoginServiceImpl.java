package com.mem.vo.business.biz.service.impl.login;

import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.dto.PcLoginRequest;
import com.mem.vo.business.biz.service.login.PcLoginService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.LoginStatus;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-08-11 23:37
 */
public class CheckManLoginServiceImpl implements PcLoginService<User> {

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @Override
    public User checkPasswd(PcLoginRequest pcLoginRequest) {
        UserQuery query = new UserQuery();
        query.setSource(SourceType.CHECKMAN.getCode());
        query.setPhoneNumber(pcLoginRequest.getPhoneNumber());
        query.setIsDelete(0);
        List<User> users = userService.findByCondition(query);
        BizAssert.notEmpty(users, BizCode.BIZ_1005.getMessage());
        if (!pcLoginRequest.getPassword().equals(users.get(0).getPassword())) {
            throw new BizException(BizCode.BIZ_1006.getMessage());
        }
        return users.get(0);
    }

    @Override
    public String getToken(PcLoginRequest pcLoginRequest, User user) {
        CommonLoginContext commonLoginContext =
                CommonLoginContext.builder()
                        .sourceCode(SourceType.CHECKMAN.getCode())
                        .bizCode(pcLoginRequest.getPhoneNumber())
                        .status(LoginStatus.SUCCESSFUL.getCode())
                        .userId(user.getId()).build();

        return tokenService.getTokenByContext(commonLoginContext, RedisPrefix.CHECK_MAN_TOKEN.getCode());
    }
}
