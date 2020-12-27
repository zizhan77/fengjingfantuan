package com.mem.vo.business.base.service.impl;


import java.util.*;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.PrizeDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.DrawLotteryUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br>
 * <b>功能：</b>PrizeService<br>
 */
@Service("prizeService")
public class PrizeServiceImpl implements PrizeService {

    private final static Logger log = LogManager.getLogger(PrizeServiceImpl.class);

    @Resource
    private PrizeDao prizeDao;
    @Resource
    private TokenService tokenService;
    @Resource
    private PrizeDService prizeDService;
    @Resource
    private ActivityUserService activityUserService;
    @Resource
    private RewardService rewardService;

    @Override
    public int insert(Prize prize) {
        return prizeDao.insert(prize);
    }


    @Override
    public int updateById(Prize prize) {
        return prizeDao.updateById(prize);
    }

    @Override
    public int deleteById(Long id) {
        return prizeDao.deleteById(id);
    }

    @Override
    public Prize findById(Long id) {
        return prizeDao.findById(id);
    }

    @Override
    public List<Prize> findByCondition(PrizeQuery query) {
        return prizeDao.findByCondition(query);
    }

    @Override
    public Page<Prize> findPageByCondition(Page page, PrizeQuery query) {
        List<Prize> list = prizeDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PrizeD slotMachine(String token, Long activityId) {
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        //抽奖次数减一
        ActivityUserQuery activityUserQuery = new ActivityUserQuery();
        activityUserQuery.setActivityId(Math.toIntExact(activityId));
        activityUserQuery.setUserId(commonLoginContext.getBizCode());
        List<ActivityUser> activityUsers = activityUserService.findByCondition(activityUserQuery);
        if (CollectionUtils.isEmpty(activityUsers) || activityUsers.get(0).getLotteryQty() <= 0) {
            throw new BizException("该用户已经没有抽奖次数！");
        }
        activityUserService.updateLotteryQtyReduceById(activityUsers.get(0).getId());
        //查找此赞助商创建的奖品
        PrizeQuery query = PrizeQuery.builder()
                .activityId(activityId)
                .build();
        List<Prize> prizeList = prizeDao.findByCondition(query);
        prizeList.sort(Comparator.comparingInt(Prize::getPrizeType));
        if (CollectionUtils.isEmpty(prizeList)) {
            return defaultPrize(commonLoginContext.getUser(),activityId);
        }
        int prizeIndex = DrawLotteryUtil.drawPrize(prizeList);
        if (prizeIndex == -1) {
            return defaultPrize(commonLoginContext.getUser(),activityId);
        }
        Prize prize = prizeList.get(prizeIndex);
        if (prize.getTotalNum() > 0 || prize.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
            PrizeDQuery prizeDQuery = PrizeDQuery
                    .builder()
                    .prizeType(prize.getPrizeType())
                    .isChange(PrizeEnum.NO_CHANGE.getCode())
                    .build();
            List<PrizeD> prizeDList = prizeDService.findByCondition(prizeDQuery);
            //如果没有，返回默认奖项
            if (prizeDList.size() == 0) {
                return defaultPrize(commonLoginContext.getUser(),activityId);
            }
            //奖品数量减一
            decreasePrizeNum(prize);
            //只有一条，直接返回
            if (prizeDList.size() == 1) {
                PrizeD prizeD = prizeDList.get(0);
                prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                prizeDService.updateById(prizeD);
                if (prizeD.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
                    integralProcess(commonLoginContext, prizeD);
                    return prizeD;
                }
                insertReward(activityId, commonLoginContext, prizeD);
                return prizeD;
            }
            //如果是积分....那
            if (prize.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
                int index = DrawLotteryUtil.drawPrizeD(prizeDList);
                PrizeD integralPrizeD = index == -1 ? defaultPrize(commonLoginContext.getUser(),activityId) : prizeDList.get(index);
                integralProcess(commonLoginContext, integralPrizeD);
                return prizeDList.get(0);
            }
            PrizeD prizeD = prizeDList.get(0);
            prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
            prizeDService.updateById(prizeD);
            insertReward(activityId, commonLoginContext, prizeD);
            return prizeD;
        }
        return defaultPrize(commonLoginContext.getUser(),activityId);
    }

    private void insertReward(Long activityId, CommonLoginContext commonLoginContext, PrizeD prizeD2) {
        Reward reward = Reward.builder()
                .prizeId(prizeD2.getId())
                .userId(commonLoginContext.getUserId().intValue())
                .prizeType(prizeD2.getPrizeType())
                .prizeName(prizeD2.getName())
                .activityId(Math.toIntExact(activityId))
                .build();
        rewardService.insert(reward);
    }

    /**
     * 积分中奖处理方法，插入积分明细表，插入中奖表，增加用户积分
     * @param commonLoginContext
     * @param integralPrizeD
     */
    private void integralProcess(CommonLoginContext commonLoginContext, PrizeD integralPrizeD) {
        Reward reward = Reward.builder()
                .prizeId(integralPrizeD.getId())
                .userId(commonLoginContext.getUserId().intValue())
                .activityId(Math.toIntExact(integralPrizeD.getActivityId()))
                .prizeType(PrizeEnum.INTEGRAL.getCode())
                .prizeName(integralPrizeD.getName())
                .integralNum(integralPrizeD.getIntegralQty())
                .build();
        rewardService.insert(reward);
    }

    /**
     * 获取一个默认奖项
     * @return 默认奖
     */
    private PrizeD defaultPrize(User user, Long activityId) {
        Reward reward = Reward.builder()
                .prizeId(0)
                .userId(Math.toIntExact(user.getId()))
                .activityId(Math.toIntExact(activityId))
                .prizeType(PrizeEnum.INTEGRAL.getCode())
                .prizeName("积分")
                .integralNum(PrizeEnum.DEFAULT_PRIZE.getCode())
                .build();
        rewardService.insert(reward);
        return PrizeD.builder().integralQty(PrizeEnum.DEFAULT_PRIZE.getCode()).prizeType(PrizeEnum.INTEGRAL.getCode()).name("积分奖励").build();
    }

    /**
     * 更新奖品主表发放奖品数量
     * @param prize 奖品
     */
    private void decreasePrizeNum(Prize prize) {
        Prize updatePrize = Prize.builder()
                .id(prize.getId())
                .givedNum(prize.getGivedNum() + 1)
                .totalNum(prize.getTotalNum() - 1)
                .build();
        prizeDao.updateById(updatePrize);
    }
}
