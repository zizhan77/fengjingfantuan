package com.mem.vo.controller.check;

import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.service.OrderService;
import com.mem.vo.business.base.service.RewardService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.RewardEnum;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-06 23:55
 */
@RestController
@RequestMapping("/code")
public class ExchangeController {

    @Resource
    private RewardService rewardService;
    @Resource
    private TokenService tokenService;
    @Resource
    private OrderService orderService;

    @Transactional(rollbackFor = Exception.class)
    @CommonExHandler(key = "兑换码兑换")
    @PostMapping("/exchange")
    public ResponseDto<Boolean> exchange(@RequestHeader("token") String token, String code) {
        if (!tokenService.isTokenEffective(token, RedisPrefix.SPONSOR_EXCHANGE_TOKEN.getCode())) {
            throw new BizException(BizCode.BIZ_1004.getMessage());
        }
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        //验证该验证码是存在且未被使用
        RewardQuery query = RewardQuery
                .builder()
                .drawCode(code)
                .status(RewardEnum.STATUS_AVAILABLE.getCode())
                .userId(Math.toIntExact(commonLoginContext.getUserId()))
                .build();
        List<Reward> rewardList = rewardService.findByCondition(query);
        BizAssert.notEmpty(rewardList, "该兑换码不存在，请重新输入!");
        Reward reward = rewardList.get(0);
        reward.setStatus(RewardEnum.STATUS_USED.getCode());
        BizAssert.isTrue(rewardService.updateById(reward) != 0, "兑换失败！请联系管理员");
        return responseDto.successData(orderService.createOrderByExchangeCode(token, code));
    }
}
