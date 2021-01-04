package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ActivityShareDao;
import com.mem.vo.business.base.dao.ActivityUserDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.ActivityEnum;
import com.mem.vo.common.constant.RedisConstant;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
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
    private IntegralService integralService;

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

    @Resource
    private RewardService rewardService;

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
                continue;
            }
            shareGroup++;
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
        }

        if (sharePerson >= ActivityEnum.PERSON_SHARE_LIMIT.getCode() && type == 0) {
            log.error("用户{}已达到每日最高分享人数上限！", shareId);
            throw new BizException("用户" + shareId + "已达到每日最高分享人数上限！");
        }

        //插入分享表，更新抽奖次数
        activityShareDao.insert(activityShare);
        ActivityUserQuery activityUserQuery = new ActivityUserQuery();
        activityUserQuery.setUserId(user.getBizCode());
        activityUserQuery.setActivityId(activityId);
        List<ActivityUser> activityUserList = activityUserDao.findByCondition(activityUserQuery);
        if (CollectionUtils.isNotEmpty(activityUserList)) {
//            //抽奖数量+1
//            ActivityUser activityUser = activityUserList.get(0);
//            activityUser.setLotteryQty(activityUser.getLotteryQty() + 1);
//            activityUserDao.updateById(activityUser);
//            result = activityUser.getLotteryQty();
            if (shareGroup + sharePerson < ActivityEnum.MAX_LUCKY_DRAW.getCode().intValue()) {
                insertIntealByUserid(shareId, activityId);
                ActivityUser activityUser = activityUserList.get(0);
                activityUser.setLotteryQty(Integer.valueOf(activityUser.getLotteryQty().intValue() + 1));
                this.activityUserDao.updateById(activityUser);
                result = activityUser.getLotteryQty().intValue();
            } else {
                insertIntealByUserid(shareId, activityId);
                ActivityUser activityUser = new ActivityUser();
                activityUser.setUserId(user.getBizCode());
                activityUser.setActivityId(activityId);
                activityUser.setLotteryQty(Integer.valueOf(1));
                result = 1;
            }
        } else {
            ActivityUser activityUser = new ActivityUser();
            activityUser.setUserId(user.getBizCode());
            activityUser.setActivityId(activityId);
            activityUser.setLotteryQty(1);
            result = 1;
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

    @Override
    public PageBean queryShareAddUserList(ActivitysharePc actPc) {
        int Commod = activityUserDao.queryShareAddUserListCount(actPc);
        PageBean<ActivitysharePc> pager = new PageBean();
        pager.setPageSize(Integer.valueOf((actPc.getPageSize() == null) ? 10 : actPc.getPageSize().intValue()));
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(Integer.valueOf((actPc.getPageNo() == null) ? 1 : actPc.getPageNo().intValue()));
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<ActivitysharePc> list = activityUserDao.queryShareAddUserList(actPc, pager);
        pager.setLists(list);
        return pager;
    }

    @Override
    public ActivityShare showShareInUser(String token) {
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        ActivityShare activityShare = this.activityShareDao.showShareInUser(contextByken.getUserId());
        IntegralRoleClass signlaxin = this.rewardService.getSignlaxin();
        Integer count = activityShare.getCount();
        activityShare.setCount(Integer.valueOf((count == null) ? 0 : count.intValue()));
        Integer sum = Integer.valueOf(activityShare.getCount().intValue() * signlaxin.getAddcount().intValue());
        activityShare.setSumofcount(Integer.valueOf((sum == null) ? 0 : sum.intValue()));
        return activityShare;
    }

    @Override
    public Integer shareForUserAndSign(String token, Long userId, Integer type, Integer shareFrom, Integer isNew) {
        User user = this.userService.findById(userId);
        if (user == null) {
            throw new BizException("该分享者不存在！");
        }

        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        String targetId = contextByken.getBizCode();
        if (targetId.equals(user.getBizCode())) {
            throw new BizException("请勿点击自己的分享！");
        }
        if (shareFrom.toString().equals("0")) {
            if (isNew.toString().equals("1")) {
                ActivityShare activityShare = new ActivityShare();
                activityShare.setUserId(user.getBizCode());
                activityShare.setTargetId(targetId);
                activityShare.setType(type);
                activityShareDao.insert(activityShare);
                insertIntealByUserid(userId, Integer.valueOf(0));
            }
            List<ActivityShare> activityShareList = activityShareDao.findAllForUserShare(userId);
            int count = 0;
            if (activityShareList != null) {
                for (ActivityShare activityShare : activityShareList) {
                    if (targetId.equals(activityShare.getTargetId())) {
                        throw new BizException("请勿重复点击！");
                    }
                    count++;
                }
            }
            if (count < 3) {
                UserShareClass userShareClass = new UserShareClass();
                userShareClass.setUserId(user.getBizCode());
                userShareClass.setTargetId(targetId);
                userShareClass.setType(type);
                userShareClass.setShareFrom(shareFrom);
                activityShareDao.insertUserShare(userShareClass);
                insertIntealByUseridAnd(userId);
            } else {
                throw new BizException("签到已满三人！");
            }
        } else if (isNew.toString().equals("1")) {
            ActivityShare activityShare = new ActivityShare();
            activityShare.setUserId(user.getBizCode());
            activityShare.setTargetId(targetId);
            activityShare.setType(type);
            Integer a = activityShareDao.selectACT(activityShare);
            if (a == null || a.intValue() > 0) {
                return Integer.valueOf(1);

            }
            activityShareDao.insert(activityShare);
            insertIntealByUserid(userId, null);
        }
        return Integer.valueOf(1);
    }

    @Override
    public List<User> getUserShareForSign(String bizCode) {
        return activityShareDao.getUserShareForSign(bizCode);
    }

    @Override
    public User getUserShareForuser(Long userId) {
        User byId = this.userService.findById(userId);
        String time = this.activityShareDao.getUserShareForuser(userId);
        if (time != null && time != "" && byId != null)
            byId.setTime(time);
        return byId;
    }

    public void insertIntealByUserid(Long shareId, Integer activityId) {
        int count = this.rewardService.getNewUserIntegral();
        Integral integralQuery = Integral.builder().userId(Integer.valueOf(shareId.intValue())).integralQty(Integer.valueOf(count)).activityId(activityId).activityName("邀请好友").type(Integer.valueOf(0)).build();
        integralService.insert(integralQuery);
    }

    public void insertIntealByUseridAnd(Long shareId) {
        int count = this.rewardService.getOldUserIntegral();
        Integral integralQuery = Integral.builder().userId(Integer.valueOf(shareId.intValue())).integralQty(Integer.valueOf(count)).activityName("好友助力").type(Integer.valueOf(0)).build();
        integralService.insert(integralQuery);
    }

}
