package com.mem.vo.business.biz.service.impl.token;

import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.service.OrganizerService;
import com.mem.vo.business.base.service.SponsorService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.RedisConstant;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 12:31
 */

@Slf4j
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private SponsorService sponsorService;

    @Resource
    private OrganizerService organizerService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserService userService;

    @Override
    public String getTokenByContext(CommonLoginContext loginContext, String preFix) {
        //先删除原来的 token
        String preToken =  redisUtils.get(RedisPrefix.USER.getCode() + loginContext.getUserId());
        if(StringUtils.isNotBlank(preToken)){
            redisUtils.del(preFix + preToken);
            redisUtils.del(RedisPrefix.USER.getCode() + loginContext.getUserId()) ;
        }
        //然后创建新的 token
        UUID token = UUID.randomUUID();
        redisUtils.setex(preFix + token.toString(), JsonUtil.toJson(loginContext),
            RedisConstant.EXPIRED_TIME_15D);
        redisUtils.setex(RedisPrefix.USER.getCode() + loginContext.getUserId(), token.toString(),
                RedisConstant.EXPIRED_TIME_15D);
        return token.toString();
    }

    @Override
    public CommonLoginContext getContextByken(String token) {
        String value = redisUtils.get(RedisPrefix.TOKEN.getCode() + token);
        CommonLoginContext commonLoginContext = JsonUtil.fromJson(value, CommonLoginContext.class);
        BizAssert.notNull(commonLoginContext, BizCode.BIZ_1004.getMessage());
        BizAssert.notNull(commonLoginContext.getUserId(),"用户id为空");

        if (commonLoginContext.getSourceCode().equals(SourceType.SPONSOR.getCode())) {
            Sponsor sponsor = this.sponsorService.findById(commonLoginContext.getUserId());
            BizAssert.notNull(sponsor, "查询用户信息为空");
                    commonLoginContext.setSponsor(sponsor);
            return commonLoginContext;
        }

        if(commonLoginContext.getSourceCode().equals(SourceType.ORGENIZER.getCode())){
            Organizer organizer=organizerService.queryById(commonLoginContext.getUserId());
            BizAssert.notNull(organizer,"查询用户信息为空");
            commonLoginContext.setOrganizer(organizer);
            return commonLoginContext;
        }else{
            User user =userService.findById(commonLoginContext.getUserId());
            BizAssert.notNull(user,"查询用户信息为空");
            commonLoginContext.setUser(user);
            return commonLoginContext;
        }
    }

    @Override
    public void prolongTime(String token) {
        redisUtils.expire(RedisPrefix.TOKEN.getCode() + token, RedisConstant.EXPIRED_TIME_2H);
    }

    @Override
    public boolean isTokenEffective(String token, String preFix) {
        if(StringUtils.isNotBlank(redisUtils.get(preFix + token))){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTokenContext(String token,CommonLoginContext loginContext) {
        redisUtils.setex(RedisPrefix.TOKEN.getCode() + token, JsonUtil.toJson(loginContext),
            RedisConstant.EXPIRED_TIME_7D);
        redisUtils.setex(RedisPrefix.USER.getCode() + loginContext.getUserId(), token.toString(),
                RedisConstant.EXPIRED_TIME_7D);
        return true;
    }

    @Override
    public boolean delteToken(String token) {
        CommonLoginContext context = getContextByken(token);
        redisUtils.del(RedisPrefix.TOKEN.getCode() + token);
        redisUtils.del(RedisPrefix.USER.getCode() + context.getUserId());
        return true;
    }

    @Override
    public boolean delteTokenByUserId(Long userId) {
        String token= redisUtils.get(RedisPrefix.USER.getCode() + userId);
        if(StringUtils.isNotBlank(token)){
            redisUtils.del(RedisPrefix.TOKEN.getCode() + token);
            redisUtils.del(RedisPrefix.USER.getCode() + userId);
        }
        return true;
    }

    @Override
    public String getTokenFifteenDay(CommonLoginContext loginContext, String preFix) {
        //然后创建新的 token
        UUID token = UUID.randomUUID();
        boolean b1 = redisUtils.setnx(preFix + token.toString(), JsonUtil.toJson(loginContext),
                RedisConstant.EXPIRED_TIME_15D);
        boolean b2 = redisUtils.setnx(RedisPrefix.USER.getCode() + loginContext.getUserId(), token.toString(),
                RedisConstant.EXPIRED_TIME_15D);
        if (b1 && b2) {
            return token.toString();
        }
        String preToken =  redisUtils.get(RedisPrefix.USER.getCode() + loginContext.getUserId());
        if (StringUtils.isNotBlank(preToken)) {
            return preToken;
        }
            redisUtils.setex(preFix + token.toString(), JsonUtil.toJson(loginContext),
                    RedisConstant.EXPIRED_TIME_15D);
            redisUtils.setex(RedisPrefix.USER.getCode() + loginContext.getUserId(), token.toString(),
                    RedisConstant.EXPIRED_TIME_15D);
        return token.toString();
    }

    @Override
    public String getSponsorToken(CommonLoginContext loginContext) {
        //然后创建新的 token
        UUID token = UUID.randomUUID();
        boolean b1 = redisUtils.setnx(RedisPrefix.SPONSOR_TOKEN.getCode() + token.toString(), JsonUtil.toJson(loginContext),
                RedisConstant.EXPIRED_TIME_15D);
        boolean b2 = redisUtils.setnx(RedisPrefix.SPONSOR.getCode() + loginContext.getUserId(), token.toString(),
                RedisConstant.EXPIRED_TIME_15D);
        if (b1 && b2) {
            return token.toString();
        }
        String preToken =  redisUtils.get(RedisPrefix.SPONSOR.getCode() + loginContext.getUserId());
        if (StringUtils.isNotBlank(preToken)) {
            return preToken;
        }
        redisUtils.setex(RedisPrefix.SPONSOR_TOKEN.getCode() + token.toString(), JsonUtil.toJson(loginContext),
                RedisConstant.EXPIRED_TIME_15D);
        redisUtils.setex(RedisPrefix.SPONSOR.getCode() + loginContext.getUserId(), token.toString(),
                RedisConstant.EXPIRED_TIME_15D);
        return token.toString();
    }
}
