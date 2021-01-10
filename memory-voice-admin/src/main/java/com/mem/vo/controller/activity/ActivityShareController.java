package com.mem.vo.controller.activity;

import com.mem.vo.business.base.model.po.ActivityShare;
import com.mem.vo.business.base.model.po.ActivitysharePc;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.service.ActivityShareService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private TokenService tokenService;

    @PostMapping({"/phone/getSharetimeForUser"})
    public ResponseDto<User> getUserShareForUser(@RequestParam Long userId) {
        ResponseDto<User> responseDto = ResponseDto.successDto();
        try {
            User user = activityShareService.getUserShareForuser(userId);
            responseDto.setData(user);
            return responseDto;
        } catch (Exception e) {
            log.error("分享用户获取用户签到接口");
            return responseDto.failSys();
        }
    }

    @PostMapping({"/phone/getUserShareForSign"})
    public ResponseDto<List<User>> getUserShareForSign(@RequestHeader("token") String token) {
        ResponseDto<List<User>> responseDto = ResponseDto.successDto();
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        try {
            List<User> list = this.activityShareService.getUserShareForSign(contextByken.getUser().getBizCode());
            responseDto.setData(list);
            return responseDto;
        } catch (Exception e) {
            log.error("好友助力接口查询有问题，用户id:{}", contextByken.getUserId(), e.toString());
            responseDto.failData("好友助力接口查询有问题");
            return responseDto;
        }
    }

    @PostMapping({"/phone/showShareInUser"})
    public ResponseDto<ActivityShare> showShareInUser(@RequestHeader("token") String token) {
        ResponseDto<ActivityShare> responseDto = ResponseDto.successDto();
        try {
            ActivityShare activityShare = activityShareService.showShareInUser(token);
            responseDto.setData(activityShare);
            return responseDto;
        } catch (Exception e) {
            log.error("分享领饭团-展示接口", e.getMessage());
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping({"/pc/queryShareAddUserList"})
    public ResponseDto<PageBean<ActivitysharePc>> queryShareAddUserList(ActivitysharePc actPc) {
        ResponseDto<PageBean> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(activityShareService.queryShareAddUserList(actPc));
        } catch (BizException e) {
            log.error("查询异常, 原因:{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询异常,参数:{}", e);
            return responseDto.failSys();
        }
    }

    @PostMapping({"/shareForUserAndSign"})
    public ResponseDto<Integer> shareForUserAndSign(@RequestHeader("token") String token, Long userId, Integer type, Integer shareFrom, Integer isNew) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(userId, BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(type, BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(shareFrom, BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(isNew, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityShareService.shareForUserAndSign(token, userId, type, shareFrom, isNew));
        } catch (BizException e) {
            log.error("签到之后分享领饭团异常, 参数:{},原因:{}", userId, e.getMessage());
            responseDto.setCode(Integer.valueOf(8));
            responseDto.setMessage("助力失败");
            return responseDto;
        } catch (Exception e) {
            log.error("签到之后分享领饭团异常, 参数:{}", userId, e);
            return responseDto.failSys();
        }
    }

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
