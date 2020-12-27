package com.mem.vo.controller.activity;

import com.mem.vo.business.base.model.po.ActivityUser;
import com.mem.vo.business.base.model.po.ActivityUserQuery;
import com.mem.vo.business.base.service.ActivityUserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-20 23:02
 */
@RestController
@RequestMapping("/activityUser")
@Slf4j
public class ActivityUserController {

    @Resource
    private ActivityUserService activityUserService;
    @Resource
    private TokenService tokenService;

    @PostMapping("/queryByToken")
    public ResponseDto<Integer> queryAllActivity(@RequestHeader("token") String token, ActivityUserQuery query) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext CommonLoginContext = tokenService.getContextByken(token);
            query.setUserId(CommonLoginContext.getBizCode());
            List<ActivityUser> activityUserList = activityUserService.findByCondition(query);
            if (CollectionUtils.isEmpty(activityUserList)) {
                throw new BizException("用户通关记录为空");
            }
            return responseDto.successData(activityUserList.get(0).getPassQty());
        } catch (BizException e) {
            log.error("获取活动信息异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取活动信息异常。", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/findByCondition")
    public ResponseDto<ActivityUser> findByCondition(@RequestHeader("token") String token, ActivityUserQuery query) {
        //权限验证
        ResponseDto<ActivityUser> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext CommonLoginContext = tokenService.getContextByken(token);
            query.setUserId(CommonLoginContext.getBizCode());
            List<ActivityUser> activityUserList = activityUserService.findByCondition(query);
            if (CollectionUtils.isEmpty(activityUserList)) {
                throw new BizException("用户通关记录为空");
            }
            return responseDto.successData(activityUserList.get(0));
        } catch (BizException e) {
            log.error("获取活动信息异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取活动信息异常。", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/inset")
    public ResponseDto<Integer> queryAllActivity(@RequestHeader("token") String token, ActivityUser activityUser) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(activityUser, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(activityUserService.insert(activityUser));
        } catch (BizException e) {
            log.error("插入活动信息异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("插入活动信息异常。", e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/update")
    public ResponseDto<Integer> update(@RequestHeader("token") String token, ActivityUser activityUser) {
        System.out.println(activityUser.getPassQty());
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(activityUser.getActivityId(), BizCode.PARAM_NULL.getMessage());
            BizAssert.notNull(activityUser.getPassQty(), BizCode.PARAM_NULL.getMessage());
            CommonLoginContext CommonLoginContext = tokenService.getContextByken(token);
            activityUser.setUserId(CommonLoginContext.getBizCode());
            return responseDto.successData(activityUserService.updateUserPassQty(activityUser));
        } catch (BizException e) {
            log.error("插入活动信息异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("插入活动信息异常。", e);
            return responseDto.failSys();
        }
    }
}
