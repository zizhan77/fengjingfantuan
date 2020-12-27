package com.mem.vo.business.biz.service.impl.login;

import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.SponsorQuery;
import com.mem.vo.business.base.service.SponsorService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.dto.PcLoginRequest;
import com.mem.vo.business.biz.service.login.PcLoginService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.LoginStatus;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.constant.SponsorEnum;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-07 23:17
 */
@Service("sponsorLoginService")
public class SponsorLoginServiceImpl implements PcLoginService<Sponsor> {

    @Resource
    private SponsorService sponsorService;
    @Resource
    private TokenService tokenService;

    @Override
    public Sponsor checkPasswd(PcLoginRequest pcLoginRequest) {
        SponsorQuery query = new SponsorQuery();
        query.setPhone(pcLoginRequest.getPhoneNumber());
        query.setPassword(pcLoginRequest.getPassword());
        query.setStatus(SponsorEnum.ON.getCode());
        List<Sponsor> sponsorList = sponsorService.findByCondition(query);
        BizAssert.notEmpty(sponsorList, BizCode.BIZ_1005.getMessage());
        if (!pcLoginRequest.getPassword().equals(sponsorList.get(0).getPassword())) {
            throw new BizException(BizCode.BIZ_1006.getMessage());
        }
        return sponsorList.get(0);
    }

    @Override
    public String getToken(PcLoginRequest pcLoginRequest, Sponsor sponsor) {
        CommonLoginContext commonLoginContext =
                CommonLoginContext.builder()
                        .sourceCode(SourceType.SPONSOR.getCode())
                        .bizCode(pcLoginRequest.getPhoneNumber())
                        .status(LoginStatus.SUCCESSFUL.getCode())
                        .userId(Long.valueOf(sponsor.getId())).build();
        return tokenService.getSponsorToken(commonLoginContext);
    }
}
