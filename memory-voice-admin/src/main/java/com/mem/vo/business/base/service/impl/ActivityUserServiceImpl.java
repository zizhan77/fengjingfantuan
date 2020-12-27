package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ActivityUserDao;
import com.mem.vo.business.base.model.po.ActivityUser;
import com.mem.vo.business.base.model.po.ActivityUserQuery;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.service.ActivityUserService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.ActivityEnum;
import com.mem.vo.common.constant.RedisConstant;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>ActivityUserService<br>
 */
@Service("activityUserService")
@Slf4j
public class ActivityUserServiceImpl implements ActivityUserService {


    @Resource
    private ActivityUserDao activityUserDao;

    @Override
    public int insert(ActivityUser activityUser) {
        return activityUserDao.insert(activityUser);
    }

    @Override
    public int updateById(ActivityUser activityUser) {
        return activityUserDao.updateById(activityUser);
    }

    @Override
    public int deleteById(Long id) {
        return activityUserDao.deleteById(id);
    }

    @Override
    public List<ActivityUser> findByCondition(ActivityUserQuery query) {
        List<ActivityUser> activityUser =  activityUserDao.findByCondition(query);
        if (CollectionUtils.isEmpty(activityUser)) {
            ActivityUser insertUser = new ActivityUser();
            insertUser.setActivityId(query.getActivityId());
            insertUser.setUserId(query.getUserId());
            insertUser.setUserName(query.getUserName());
            activityUserDao.insert(insertUser);
            return Arrays.asList(insertUser);
        }
        return activityUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUserPassQty(ActivityUser activityUser) {
        ActivityUserQuery query = new ActivityUserQuery();
        query.setUserId(activityUser.getUserId());
        query.setActivityId(activityUser.getActivityId());
        List<ActivityUser> activityUsers =  activityUserDao.findByCondition(query);
        if (CollectionUtils.isEmpty(activityUsers)) {
            ActivityUser insertUser = new ActivityUser();
            insertUser.setUserId(activityUser.getUserId());
            insertUser.setActivityId(activityUser.getActivityId());
            insertUser.setUserName(activityUser.getUserName());
            activityUserDao.insert(insertUser);
        }
        //达到最低通关次数，摇奖次数给5次
        if (activityUser.getPassQty().equals(ActivityEnum.MINIMUM_PASS_QTY.getCode())) {
            activityUser.setLotteryQty(ActivityEnum.BASE_LOTTERY_QTY.getCode());
        }
        System.out.println("更新前的关数"+activityUser.getPassQty());
        System.out.println("更新前的ID"+activityUser.getUserId());
        System.out.println("更新前的活动"+activityUser.getActivityId());
        int  row = activityUserDao.updateByUserIdAndActivityId(activityUser.getPassQty(),activityUser.getUserId(),activityUser.getActivityId());
        if (row == 0) {
            throw new BizException("更新通关次数失败");
        }
        return activityUser.getPassQty();
    }

    @Override
    public int updateLotteryQtyReduceById(long id) {
        return activityUserDao.updateLotteryQtyReduceById(id);
    }

}
