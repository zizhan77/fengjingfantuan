package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.RewardDao;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.base.service.IntegralService;
import com.mem.vo.business.base.service.RewardService;
import com.mem.vo.common.constant.ActivityEnum;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>RewardService<br>
 */
@Service("rewardService")
public class RewardServiceImpl implements RewardService {
    private final static Logger log = LogManager.getLogger(RewardServiceImpl.class);


    @Resource
    private RewardDao rewardDao;
    @Resource
    private IntegralService integralService;
    @Resource
    private ActivityService activityService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(Reward prizeD) {
        //每次插入积分都要更新积分表
        if (prizeD.getPrizeType() != null && prizeD.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
            String activityName = "";
            if (prizeD.getActivityId().equals(ActivityEnum.ACTIVITY_USER_INFO.getCode())) {
                activityName = ActivityEnum.ACTIVITY_USER_INFO.getName();
            } else {
                Activity activity = activityService.findById(Long.valueOf(prizeD.getActivityId()));
                activityName = activity == null ? "" : activity.getActivityTitle();
            }

            Integral integralQuery = Integral.builder()
                    .userId(prizeD.getUserId())
                    .integralQty(prizeD.getIntegralNum())
                    .activityId(prizeD.getActivityId())
                    .activityName(activityName)
                    .type(0)
                    .build();
            integralService.insert(integralQuery);
        }
        return rewardDao.insert(prizeD);
    }

    @Override
    public int updateById(Reward reward) {
        return rewardDao.updateById(reward);
    }

    @Override
    public int deleteById(Long id) {
        return rewardDao.deleteById(id);
    }

    @Override
    public Reward findById(Long id) {
        return rewardDao.findById(id);
    }

    @Override
    public List<Reward> findByCondition(RewardQuery query) {
        return rewardDao.findByCondition(query);
    }

    @Override
    public List<Reward> queryMaterialAndTicketList(RewardQuery query) {
        return rewardDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page, RewardQuery query) {
        rewardDao.findByCondition(page, query);
    }

    @Override
    public List<Integer> selectIdByEt(RewardQuery query) {
        return rewardDao.selectIdByEt(query);
    }
}
