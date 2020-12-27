package com.mem.vo.business.biz.service.impl.login;

import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.service.OrganizerService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.dto.OrganizerLoginRequest;
import com.mem.vo.business.biz.service.login.OrganizerLoginService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.LoginStatus;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Service("OrganizerService")
public class OrganizerLoginServiceImpl implements OrganizerLoginService {
    @Autowired
    private OrganizerService OrganizerService;
    @Autowired
    private TokenService tokenService;
    @Override
    public Organizer checkPasswd(OrganizerLoginRequest organizerLoginRequest) {
        String phone = organizerLoginRequest.getPhone();
        String password = organizerLoginRequest.getPassword();
        List<Organizer> list = OrganizerService.queryByUsername(phone);

        BizAssert.notEmpty(list, BizCode.BIZ_1006.getMessage());
        System.out.println(21312);
        if(!list.get(0).getPassword().equals(password)){
            throw new BizException(BizCode.BIZ_1006.getMessage());
        }
        return list.get(0);
    }

    @Override
    public String getToken(OrganizerLoginRequest organizerLoginRequest, Organizer organizer) {
        CommonLoginContext commonLoginContext =
                CommonLoginContext.builder()
                        .sourceCode(SourceType.ORGENIZER.getCode())
                        .status(LoginStatus.SUCCESSFUL.getCode())
                        .userId(Long.parseLong(organizer.getId()+""))
                        .organizer(organizer)
                        .build();
        String token = tokenService.getTokenByContext(commonLoginContext, RedisPrefix.TOKEN.getCode());
        return token;
    }

    @Override
    public boolean loginOut(String token) {

        return tokenService.delteToken(token);
    }
}
