package com.mem.vo.controller.activity;

import com.mem.vo.business.base.model.po.ActivityUser;
import com.mem.vo.business.base.model.po.ActivityUserQuery;
import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.service.ActivityUserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping({"/phone/queryRewardByUser/1"})
    public ResponseDto<PageBean<Prize>> queryRewardByUser(@RequestHeader("token") String token, @RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam Integer activityId) {
        ResponseDto<PageBean<Prize>> responseDto = ResponseDto.successDto();
        try {
            PageBean<Prize> page = activityUserService.queryRewardByActivity(token, pageNo, pageSize, activityId);
            responseDto.setData(page);
            return responseDto;
        } catch (Exception e) {
            log.error("老虎机下面的三个接口其中之一有问题", e.getMessage());
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping({"/phone/queryRewardByUser/2"})
    public ResponseDto<PageBean<Prize>> queryRewardByUser1(@RequestHeader("token") String token, @RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam Integer activityId) {
        ResponseDto<PageBean<Prize>> responseDto = ResponseDto.successDto();
        try {
            PageBean<Prize> page = activityUserService.queryRewardByActivityAndUser(token, pageNo, pageSize, activityId);
            responseDto.setData(page);
            return responseDto;
        } catch (Exception e) {
            log.error("老虎机下面的三个接口其中之一有问题", e.getMessage());
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

    @PostMapping({"/phone/queryRewardByUser/3"})
    public ResponseDto<PageBean<User>> queryRewardByUser2(@RequestHeader("token") String token, @RequestParam Integer pageNo, @RequestParam Integer pageSize, Integer activityId) {
        ResponseDto<PageBean<User>> responseDto = ResponseDto.successDto();
        try {
            PageBean<User> page = activityUserService.queryShareUserbyUser(token, pageNo, pageSize, activityId);
            responseDto.setData(page);
            return responseDto;
        } catch (Exception e) {
            log.error("老虎机下面的三个接口其中之一有问题", e.getMessage());
            responseDto.setCode(Integer.valueOf(1));
            return responseDto;
        }
    }

//    @PostMapping("/queryByToken")
//    public ResponseDto<Integer> queryAllActivity(@RequestHeader("token") String token, ActivityUserQuery query) {
//        //权限验证
//        ResponseDto<Integer> responseDto = ResponseDto.successDto();
//        try {
//            CommonLoginContext CommonLoginContext = tokenService.getContextByken(token);
//            query.setUserId(CommonLoginContext.getBizCode());
//            List<ActivityUser> activityUserList = activityUserService.findByCondition(query);
//            if (CollectionUtils.isEmpty(activityUserList)) {
//                throw new BizException("用户通关记录为空");
//            }
//            return responseDto.successData(activityUserList.get(0).getPassQty());
//        } catch (BizException e) {
//            log.error("获取活动信息异常，原因：{}", e.getMessage());
//            return responseDto.failData(e.getMessage());
//        } catch (Exception e) {
//
//            log.error("获取活动信息异常。", e);
//            return responseDto.failSys();
//        }
//    }

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
            CommonLoginContext contextByken = tokenService.getContextByken(token);
            String bizCode = contextByken.getBizCode();
            activityUser.setUserId(bizCode);
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

    @PostMapping({"/shareAndAdd"})
    public ResponseDto<Integer> shareAndAdd(@RequestHeader("token") String token, String activityId) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(activityId, BizCode.PARAM_NULL.getMessage());
            Integer integer = activityUserService.shareAndAdd(token, activityId);
            responseDto.setData(integer);
            return responseDto;
        } catch (BizException e) {
            log.error("插入活动信息异常, 原因: {}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("插入活动信息异常", e);
            return responseDto.failSys();
        }
    }
}
