package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ActivityUserDao;
import com.mem.vo.business.base.model.po.ActivityUser;
import com.mem.vo.business.base.model.po.ActivityUserQuery;
import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.service.ActivityUserService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.ActivityEnum;
import com.mem.vo.common.constant.IsShare;
import com.mem.vo.common.constant.RedisConstant;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.DateUtil;
import com.mem.vo.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>ActivityUserService<br>
 */
@Service("activityUserService")
@Slf4j
public class ActivityUserServiceImpl implements ActivityUserService {

    @Resource
    private TokenService tokenService;

    @Resource
    private UserService userService;

    @Resource
    private ActivityUserDao activityUserDao;

    @Override
    public int insert(ActivityUser activityUser) {

        ActivityUserQuery activityUserQuery = new ActivityUserQuery();
        activityUserQuery.setUserId(activityUser.getUserId());
        activityUserQuery.setActivityId(activityUser.getActivityId());
        List<ActivityUser> byCondition = this.activityUserDao.findByCondition(activityUserQuery);
        if (byCondition.size() == 0) {
            return this.activityUserDao.insert(activityUser);
        }
        return 0;
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
//            activityUser.setLotteryQty(ActivityEnum.BASE_LOTTERY_QTY.getCode());
            ActivityUserQuery activityUserQuery = new ActivityUserQuery();
            activityUserQuery.setUserId(activityUser.getUserId());
            activityUserQuery.setPassQty(ActivityEnum.MINIMUM_PASS_QTY.getCode());
            List<ActivityUser> byCondition = activityUserDao.findByCondition(activityUserQuery);
            if (byCondition.size() == 0) {
                userService.addIntegral(ActivityEnum.BASE_ADD_INTEGRAL.getCode(), activityUser.getUserId());
            }
            List<ActivityUser> list = activityUserDao.findTodayByUserIdAndActivityId(activityUser.getUserId(), activityUser.getActivityId(), ActivityEnum.MINIMUM_PASS_QTY.getCode());
            if (list.size() == 0) {
                activityUser.setLotteryQty(ActivityEnum.BASE_LOTTERY_QTY.getCode());
            }
        }
        System.out.println("更新前的关数"+activityUser.getPassQty());
        System.out.println("更新前的ID"+activityUser.getUserId());
        System.out.println("更新前的活动"+activityUser.getActivityId());
        activityUserDao.updateByUserIdAndActivityId(activityUser.getPassQty(),activityUser.getUserId(),activityUser.getActivityId(), DateUtil.formatDateStr(new Date()), activityUser.getLotteryQty());
//        if (row == 0) {
//            throw new BizException("更新通关次数失败");
//        }
        return activityUser.getPassQty();
    }

    @Override
    public int updateLotteryQtyReduceById(long id) {
        return activityUserDao.updateLotteryQtyReduceById(id);
    }

    @Override
    public int findCountPass() {
        return activityUserDao.findCountPass();
    }

    @Override
    public Integer shareAndAdd(String token, String activityId) {
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        String bizCode = commonLoginContext.getBizCode();
        ActivityUserQuery activityUser = new ActivityUserQuery();
        activityUser.setUserId(bizCode);
        activityUser.setActivityId(Integer.valueOf(Integer.parseInt(activityId)));
        List<ActivityUser> list = activityUserDao.findByCondition(activityUser);
        if (list.size() > 0 && list.get(0).getIsShare().equals(IsShare.IS.getCode())) {
            throw new BizException("你已经分享过此活动，不能再为你增加抽奖次数");
        }
        Integer integer = activityUserDao.updateLotteryQt(activityId, bizCode);
        if (integer.intValue() == 0) {
            throw new BizException("你已经分享过此活动，不能再为你增加抽奖次数");
        }
        return integer;
    }

    @Override
    public PageBean<Prize> queryRewardByActivity(String token, Integer pageNo, Integer pageSize, Integer activityId) {
        int Commod = activityUserDao.queryRewardByActivityCount(activityId);
        PageBean<Prize> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<Prize> list = activityUserDao.queryRewardByActivity(pager, activityId);
        System.out.println(list.size());
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<Prize> queryRewardByActivityAndUser(String token, Integer pageNo, Integer pageSize, Integer activityId) {
        CommonLoginContext commonLoginContext = this.tokenService.getContextByken(token);
        int Commod = activityUserDao.queryRewardByActivityAndUserCount(activityId, commonLoginContext.getUserId());
        PageBean<Prize> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<Prize> list = activityUserDao.queryRewardByActivityAndUser(pager, activityId, commonLoginContext.getUserId());
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<User> queryShareUserbyUser(String token, Integer pageNo, Integer pageSize, Integer activityId) {
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        int Commod = activityUserDao.queryShareUserbyUserCount(activityId, commonLoginContext.getUserId());
        PageBean<User> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<User> list = activityUserDao.queryShareUserbyUser(pager, activityId, commonLoginContext.getUserId());
        pager.setLists(list);
        return pager;
    }

}
