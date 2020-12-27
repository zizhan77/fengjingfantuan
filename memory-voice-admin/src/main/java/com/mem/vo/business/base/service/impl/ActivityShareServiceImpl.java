package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ActivityShareDao;
import com.mem.vo.business.base.dao.ActivityUserDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.ActivityShareService;
import com.mem.vo.business.base.service.ActivityUserService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.ActivityEnum;
import com.mem.vo.common.constant.RedisConstant;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br>
 * <b>功能：</b>ActivityShareService<br>
 */
@Service("activityShareService")
public class ActivityShareServiceImpl implements ActivityShareService {
    private final static Logger log = LogManager.getLogger(ActivityShareServiceImpl.class);


    @Resource
    private ActivityShareDao activityShareDao;
    @Resource
    private TokenService tokenService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ActivityUserDao activityUserDao;
    @Resource
    private UserService userService;

    @Override
    public int insert(ActivityShare activityShare) {
        return activityShareDao.insert(activityShare);
    }

    @Override
    public int updateById(ActivityShare activityShare) {
        return activityShareDao.updateById(activityShare);
    }

    @Override
    public int deleteById(Long id) {
        return activityShareDao.deleteById(id);
    }

    @Override
    public ActivityShare findById(Long id) {
        return activityShareDao.findById(id);
    }

    @Override
    public List<ActivityShare> findByCondition(ActivityShareQuery query) {
        return activityShareDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page, ActivityShareQuery query) {
        activityShareDao.findByCondition(page, query);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateLotteryQty(String token, Long shareId, Integer type, Integer activityId) {
        User user = userService.findById(shareId);
        //没有该用户
        if (user == null) {
            throw new BizException("该分享者不存在！");
        }
       /* //查询今日分享列表
        ActivityShareQuery query = new ActivityShareQuery();
        query.setUserId(user.getBizCode());
        query.setActivityId(activityId);*/
        List<ActivityShare> activityShareList = activityShareDao.findAll();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        String targetId = commonLoginContext.getBizCode();

        if(targetId.equals(user.getBizCode())){
            throw new BizException("请勿点击自己的分享！");
        }
        int shareGroup = 0, sharePerson = 0;
        for (ActivityShare activityShare : activityShareList) {
            System.out.println("ID"+activityShare.getUserId());
            System.out.println("Type"+activityShare.getType());
            if (targetId.equals(activityShare.getTargetId())&&activityShare.getUserId().equals(user.getBizCode())) {
                throw new BizException("请勿重复点击！");
            }
            if (activityShare.getType() == 0) {
                sharePerson++;
            } else {
                shareGroup++;
            }
        }
        ActivityShare activityShare = new ActivityShare();
        activityShare.setUserId(user.getBizCode());
        activityShare.setTargetId(targetId);
        activityShare.setType(type);
        activityShare.setActivityId(activityId);
        int result = 0;
        if (shareGroup >= ActivityEnum.GROUP_SHARE_LIMIT.getCode() && type == 1) {
            log.error("用户{}已达到每日最高分享群上限！", shareId);
            throw new BizException("用户" + shareId + "已达到每日最高分享群上限！");
        } else if (sharePerson >= ActivityEnum.PERSON_SHARE_LIMIT.getCode() && type == 0) {
            log.error("用户{}已达到每日最高分享人数上限！", shareId);
            throw new BizException("用户" + shareId + "已达到每日最高分享人数上限！");
        } else {
            //插入分享表，更新抽奖次数
            activityShareDao.insert(activityShare);
            ActivityUserQuery activityUserQuery = new ActivityUserQuery();
            activityUserQuery.setUserId(user.getBizCode());
            activityUserQuery.setActivityId(activityId);
            List<ActivityUser> activityUserList = activityUserDao.findByCondition(activityUserQuery);
            if (CollectionUtils.isNotEmpty(activityUserList)) {
                //抽奖数量+1
                ActivityUser activityUser = activityUserList.get(0);
                activityUser.setLotteryQty(activityUser.getLotteryQty() + 1);
                activityUserDao.updateById(activityUser);
                result = activityUser.getLotteryQty();
            } else {
                ActivityUser activityUser = new ActivityUser();
                activityUser.setUserId(user.getBizCode());
                activityUser.setActivityId(activityId);
                activityUser.setLotteryQty(1);
                result = 1;
            }
        }
        return result;
    }

    @Override
    public Integer queryLotteryQtyByToken(String token, Integer activityId) {
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        String userId = commonLoginContext.getBizCode();
        ActivityUserQuery query = new ActivityUserQuery();
        query.setUserId(userId);
        query.setActivityId(activityId);
        List<ActivityUser> activityUserList = activityUserDao.findByCondition(query);
        return CollectionUtils.isEmpty(activityUserList) ? 0 : activityUserList.get(0).getLotteryQty();
    }


}
