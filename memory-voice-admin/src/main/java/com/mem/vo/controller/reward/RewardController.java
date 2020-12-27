package com.mem.vo.controller.reward;

import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.service.RewardService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 中奖详情
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-23 10:41
 */
@RestController
@RequestMapping("/reward")
@Slf4j
public class RewardController {

    @Resource
    private RewardService rewardService;
    @Resource
    private TokenService tokenService;

    @PostMapping("/queryAll")
    public ResponseDto<List<Reward>> queryAllPrize(@RequestHeader("token") String token) {
        //权限验证
        ResponseDto<List<Reward>> responseDto = ResponseDto.successDto();

        try {
            return responseDto.successData(rewardService.findByCondition(new RewardQuery()));
        } catch (BizException e) {
            log.error("prized.queryAll异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("prized.queryAll异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryMaterialAndTicketList")
    public ResponseDto<List<Reward>> queryMaterialAndTicketList(@RequestHeader("token") String token, Integer activityId){
        //权限验证
        ResponseDto<List<Reward>> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext commonLoginContext = tokenService.getContextByken(token);

            List<Integer> prizeIdList = new ArrayList<>();
            prizeIdList.add(PrizeEnum.TICKET.getCode());
            prizeIdList.add(PrizeEnum.MATERIAL.getCode());
            RewardQuery query = RewardQuery
                    .builder()
                    .userId(Math.toIntExact(commonLoginContext.getUserId()))
                    .prizeIdList(prizeIdList)

                    .build();
            return responseDto.successData(rewardService.queryMaterialAndTicketList(query));
        } catch (BizException e) {
            log.error("prized.queryListByCondition异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("prized.queryListByCondition异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryListByCondition")
    public ResponseDto<List<Reward>> queryAllPrize(@RequestHeader("token") String token, RewardQuery query) {
        //权限验证
        ResponseDto<List<Reward>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(query, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(rewardService.findByCondition(query));
        } catch (BizException e) {
            log.error("prized.queryListByCondition异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("prized.queryListByCondition异常", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/insert")
    public ResponseDto<Integer> inset(@RequestHeader("token") String token, Reward reward) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(reward, BizCode.PARAM_NULL.getMessage());
            CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
            reward.setUserId(Math.toIntExact(commonLoginContext.getUserId()));
            return responseDto.successData(rewardService.insert(reward));
        } catch (BizException e) {
            log.error("prized.insert，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("prized.insert", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/update")
    public ResponseDto<Integer> update(@RequestHeader("token") String token, Reward reward) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(reward, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(rewardService.updateById(reward));
        } catch (BizException e) {
            log.error("prized.update，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("prized.update", e);
            return responseDto.failSys();
        }
    }
}
