package com.mem.vo.controller.activity;

import com.mem.vo.business.base.model.po.ActivityShare;
import com.mem.vo.business.base.service.ActivityShareService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-22 16:15
 */
@RestController
@RequestMapping("/activityShare")
@Slf4j
public class ActivityShareController {

    @Resource
    private ActivityShareService activityShareService;


    @PostMapping("/updateShareQty")
    public ResponseDto<Integer> updateLotteryQty(@RequestHeader("token") String token, Long userId, Integer type, Integer activityId) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        System.out.println(userId);
        try {
            BizAssert.notNull(userId, BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(type, BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(activityId, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityShareService.updateLotteryQty(token,userId, type, activityId));
        } catch (BizException e) {

            log.error("更新活动异常，参数:{},原因：{}", userId, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("更新活动异常，参数:{}", userId, e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/queryLotteryQty")
    public ResponseDto<Integer> queryLotteryQty(@RequestHeader("token") String token, Integer activityId) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        BizAssert.notNull(activityId, BizCode.PARAM_NULL.getMessage());
        try {
            return responseDto.successData(activityShareService.queryLotteryQtyByToken(token, activityId));
        } catch (BizException e) {

            log.error("查询可抽奖次数异常，参数:{},原因：{}", token, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询可抽奖次数异常，参数:{}", token, e);
            return responseDto.failSys();
        }
    }
}
