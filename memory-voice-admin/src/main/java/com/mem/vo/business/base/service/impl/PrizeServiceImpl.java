package com.mem.vo.business.base.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.PrizeDao;
import com.mem.vo.business.base.dao.RewardDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.IsDeleteEnum;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.DateUtil;
import com.mem.vo.common.util.DrawLotteryUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
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
    private ChangeCodeService changeCodeService;

    @Resource
    private PrizeService prizeService;

    @Resource
    private CodeTypeService codeTypeService;

    @Resource
    private PrizeDao prizeDao;

    @Resource
    private RewardDao rewardDao;

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
        Prize prize = prizeDao.findById(id);
        if (Objects.nonNull(prize)) {
            int retCount = codeTypeService.addCount(prize.getCodeType(), prize.getTotalNum());
            return prizeDao.deleteById(id);
        }
        return 0;
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
//        log.error("=======================slotMachine=========================");
//        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
//        //抽奖次数减一
//        ActivityUserQuery activityUserQuery = new ActivityUserQuery();
//        activityUserQuery.setActivityId(Math.toIntExact(activityId));
//        activityUserQuery.setUserId(commonLoginContext.getBizCode());
//        List<ActivityUser> activityUsers = activityUserService.findByCondition(activityUserQuery);
//        if (CollectionUtils.isEmpty(activityUsers) || activityUsers.get(0).getLotteryQty() <= 0) {
//            throw new BizException("该用户已经没有抽奖次数！");
//        }
//        activityUserService.updateLotteryQtyReduceById(activityUsers.get(0).getId());
//        //查找此赞助商创建的奖品
//        PrizeQuery query = PrizeQuery.builder()
//                .activityId(activityId)
//                .build();
//        List<Prize> prizeList = prizeDao.findByCondition(query);
//        prizeList.sort(Comparator.comparingInt(Prize::getPrizeType));
//        if (CollectionUtils.isEmpty(prizeList)) {
//            return defaultPrize(commonLoginContext.getUser(),activityId);
//        }
//        int prizeIndex = DrawLotteryUtil.drawPrize(prizeList);
//        if (prizeIndex == -1) {
//            return defaultPrize(commonLoginContext.getUser(),activityId);
//        }
//        Prize prize = prizeList.get(prizeIndex);
//        if (prize.getTotalNum() > 0 || prize.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
//            PrizeDQuery prizeDQuery = PrizeDQuery
//                    .builder()
//                    .prizeType(prize.getPrizeType())
//                    .isChange(PrizeEnum.NO_CHANGE.getCode())
//                    .build();
//            List<PrizeD> prizeDList = prizeDService.findByCondition(prizeDQuery);
//            //如果没有，返回默认奖项
//            if (prizeDList.size() == 0) {
//                return defaultPrize(commonLoginContext.getUser(),activityId);
//            }
//            //奖品数量减一
//            decreasePrizeNum(prize);
//            //只有一条，直接返回
//            if (prizeDList.size() == 1) {
//                PrizeD prizeD = prizeDList.get(0);
//                prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
//                prizeDService.updateById(prizeD);
//                if (prizeD.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
//                    integralProcess(commonLoginContext, prizeD);
//                    return prizeD;
//                }
//                insertReward(activityId, commonLoginContext, prizeD);
//                return prizeD;
//            }
//            //如果是积分....那
//            if (prize.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
//                int index = DrawLotteryUtil.drawPrizeD(prizeDList);
//                PrizeD integralPrizeD = index == -1 ? defaultPrize(commonLoginContext.getUser(),activityId) : prizeDList.get(index);
//                integralProcess(commonLoginContext, integralPrizeD);
//                return prizeDList.get(0);
//            }
//            PrizeD prizeD = prizeDList.get(0);
//            prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
//            prizeDService.updateById(prizeD);
//            insertReward(activityId, commonLoginContext, prizeD);
//            return prizeD;
//        }
//        return defaultPrize(commonLoginContext.getUser(),activityId);
        log.error("=======================slotMachine=========================");
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        ActivityUserQuery activityUserQuery = new ActivityUserQuery();
        activityUserQuery.setActivityId(Integer.valueOf(Math.toIntExact(activityId.longValue())));
        activityUserQuery.setUserId(commonLoginContext.getBizCode());
        List<ActivityUser> activityUsers = activityUserService.findByCondition(activityUserQuery);
        if (CollectionUtils.isEmpty(activityUsers) || activityUsers.get(0).getLotteryQty().intValue() <= 0) {
            throw new BizException("该用户已经没有抽奖次数");
        }
        activityUserService.updateLotteryQtyReduceById(activityUsers.get(0).getId().longValue());
        PrizeQuery query = PrizeQuery.builder().activityId(activityId).build();
        List<Prize> prizeList = prizeDao.findByCondition(query);
        prizeList.sort(Comparator.comparingInt(Prize::getPrizeType));
        if (CollectionUtils.isEmpty(prizeList)) {
            return defaultPrize(commonLoginContext.getUser(), activityId);
        }
        int prizeIndex = DrawLotteryUtil.drawPrize(prizeList);
        if (prizeIndex == -1) {
            return defaultPrize(commonLoginContext.getUser(), activityId);
        }
        Prize prize = prizeList.get(prizeIndex);
        RewardQuery rewardQuery = RewardQuery.builder().prizeType(prize.getPrizeType()).time(new Date()).build();
        List<Reward> currentMonthList = rewardService.findByConditionAndDate(rewardQuery);
        List<Reward> currentDayTicketList = Lists.newArrayList();
        for (Reward reward : currentMonthList) {
            PrizeD prizeD = prizeDService.findById(Long.valueOf((reward.getPrizeId() == null) ? 0L : reward.getPrizeId().intValue()));
            if (Objects.isNull(prizeD) || Objects.isNull(prizeD.getPrizeId())) {
                continue;
            }
            if (PrizeEnum.coupon.getCode().equals(reward.getPrizeType())) {
                Prize p = prizeService.findById(Long.valueOf(prizeD.getPrizeId().intValue()));
                if (Objects.nonNull(p) && Objects.nonNull(p.getCodeType()) && prize.getCodeType().equals(p.getCodeType()) && reward.getUserId().equals(Integer.valueOf(commonLoginContext.getUserId().intValue()))) {
                    return defaultPrize(commonLoginContext.getUser(), activityId);
                }
                continue;
            }
            if (PrizeEnum.MATERIAL.getCode().equals(prize.getPrizeType())) {
                if (activityId.equals(Long.valueOf(reward.getActivityId().intValue())) && prize.getId().equals(prizeD.getPrizeId()) && reward
                        .getUserId().equals(Integer.valueOf(commonLoginContext.getUserId().intValue()))) {
                    return defaultPrize(commonLoginContext.getUser(), activityId);
                }
                continue;
            }
            if (PrizeEnum.TICKET.getCode().equals(prize.getPrizeType()) &&
                    activityId.equals(Long.valueOf(reward.getActivityId().intValue())) && prize.getId().equals(prizeD.getPrizeId())) {
                if (StringUtils.equals(DateUtil.formatDatetime(reward.getTime()), DateUtil.formatDatetime(new Date()))) {
                    currentDayTicketList.add(reward);
                }
                if (reward.getUserId().equals(Integer.valueOf(commonLoginContext.getUserId().intValue()))) {
                    return defaultPrize(commonLoginContext.getUser(), activityId);
                }
            }
        }
        if (Objects.nonNull(prize.getDailyTicketLimit()) && currentDayTicketList.size() >= prize.getDailyTicketLimit().intValue()) {
            log.error("============当日门票放出数量已达上线===========");
            return defaultPrize(commonLoginContext.getUser(), activityId);
        }
        if (prize.getTotalNum().intValue() > 0 || prize.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
            PrizeDQuery prizeDQuery = PrizeDQuery.builder().prizeId(prize.getId()).isChange(PrizeEnum.NO_CHANGE.getCode()).build();
            List<PrizeD> prizeDList = prizeDService.findByCondition(prizeDQuery);
            if (prizeDList.size() == 0) {
                return defaultPrize(commonLoginContext.getUser(), activityId);
            }
            if (prizeDList.size() == 1) {
                PrizeD prizeD1 = prizeDList.get(0);
                if (prizeD1.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
                    integralProcess(commonLoginContext, prizeD1);
                    return prizeD1;
                }
                if (prizeD1.getPrizeType().equals(PrizeEnum.MATERIAL.getCode())) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.add(2, 0);
                    c.set(5, 1);
                    String first = format.format(c.getTime());
                    Calendar ca = Calendar.getInstance();
                    ca.set(5, ca.getActualMaximum(5));
                    String last = format.format(ca.getTime());
                    List<Reward> usergetTicket = rewardDao.getUserForTickrt(commonLoginContext.getUserId(), first, last, PrizeEnum.MATERIAL.getCode());
                    if (usergetTicket != null && usergetTicket.size() > 0) {
                        return defaultPrize(commonLoginContext.getUser(), activityId);
                    }
                    prizeD1.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                    prizeDService.updateById(prizeD1);
                    prizeD1.setStatus(Integer.valueOf(0));
                    insertReward(activityId, commonLoginContext, prizeD1);
                    return prizeD1;
                }
                if (prizeD1.getPrizeType().equals(PrizeEnum.TICKET.getCode())) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar c = Calendar.getInstance();
                    c.add(2, 0);
                    c.set(5, 1);
                    String first = format.format(c.getTime());
                    Calendar ca = Calendar.getInstance();
                    ca.set(5, ca.getActualMaximum(5));
                    String last = format.format(ca.getTime());
                    List<Reward> usergetTicket = rewardDao.getUserForTickrt(commonLoginContext.getUserId(), first, last, PrizeEnum.TICKET.getCode());
                    if (usergetTicket != null && usergetTicket.size() > 0) {
                        return defaultPrize(commonLoginContext.getUser(), activityId);
                    }
                    prizeD1.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                    prizeDService.updateById(prizeD1);
                    prizeD1.setStatus(Integer.valueOf(0));
                    insertReward(activityId, commonLoginContext, prizeD1);
                    return prizeD1;
                }
                prizeD1.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                prizeDService.updateById(prizeD1);
                insertReward(activityId, commonLoginContext, prizeD1);
                return prizeD1;
            }
            if (prize.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
                int index = DrawLotteryUtil.drawPrizeD(prizeDList);
                PrizeD integralPrizeD = (index == -1) ? defaultPrize(commonLoginContext.getUser(), activityId) : prizeDList.get(index);
                integralProcess(commonLoginContext, integralPrizeD);
                return integralPrizeD;
            }
            int x = (int)(Math.random() * (prizeDList.size() - 1));
            PrizeD prizeD = prizeDList.get(x);
            Prize byId = prizeService.findById(Long.valueOf(prizeD.getPrizeId().longValue()));
            System.out.println("prizeID" + prizeD.getPrizeId());
            Integer currentRewardId = Integer.valueOf(0);
            if (byId.getPrizeType().equals(PrizeEnum.coupon.getCode())) {
                Integer codeType = byId.getCodeType();
                System.out.println("codeType" + codeType);
                prizeD.setCodeType(codeType);
                List<ChangeCode> list = changeCodeService.selectByCodeTypeId(codeType, IsDeleteEnum.YES.getCode());
                if (list.size() == 0) {
                    return defaultPrize(commonLoginContext.getUser(), activityId);
                }
                int c = (int)(Math.random() * (list.size() - 1));
                ChangeCode changeCode = list.get(c);
                int i = changeCodeService.updateById(changeCode.getId(), IsDeleteEnum.NO.getCode());
                if (i == 0) {
                    return defaultPrize(commonLoginContext.getUser(), activityId);
                }
                CodeType codeType1 = codeTypeService.selectById(codeType);
                prizeD.setCodeTypeBean(codeType1);
                prizeD.setKeyId(changeCode.getId());
                prizeD.setChangeCode(changeCode);
                prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                prizeDService.updateById(prizeD);
                prizeD.setMiniType(prize.getMiniType());
                currentRewardId = insertReward(activityId, commonLoginContext, prizeD);
            } else if (prizeD.getPrizeType().equals(PrizeEnum.TICKET.getCode())) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.add(2, 0);
                c.set(5, 1);
                String first = format.format(c.getTime());
                Calendar ca = Calendar.getInstance();
                ca.set(5, ca.getActualMaximum(5));
                String last = format.format(ca.getTime());
                List<Reward> usergetTicket = rewardDao.getUserForTickrt(commonLoginContext.getUserId(), first, last, PrizeEnum.TICKET.getCode());
                if (usergetTicket != null && usergetTicket.size() > 0) {
                    return defaultPrize(commonLoginContext.getUser(), activityId);
                }
                prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                prizeDService.updateById(prizeD);
                prizeD.setMiniType(prize.getMiniType());
                prizeD.setStatus(Integer.valueOf(0));
                currentRewardId = insertReward(activityId, commonLoginContext, prizeD);
            } else if (prizeD.getPrizeType().equals(PrizeEnum.MATERIAL.getCode())) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                c.add(2, 0);
                c.set(5, 1);
                String first = format.format(c.getTime());
                Calendar ca = Calendar.getInstance();
                ca.set(5, ca.getActualMaximum(5));
                String last = format.format(ca.getTime());
                List<Reward> usergetTicket = rewardDao.getUserForTickrt(commonLoginContext.getUserId(), first, last, PrizeEnum.MATERIAL.getCode());
                if (usergetTicket != null && usergetTicket.size() > 0) {
                    return defaultPrize(commonLoginContext.getUser(), activityId);
                }
                prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                prizeDService.updateById(prizeD);
                prizeD.setMiniType(prize.getMiniType());
                prizeD.setStatus(0);
                currentRewardId = insertReward(activityId, commonLoginContext, prizeD);
            } else {
                prizeD.setIsChange(PrizeEnum.YES_CHANGE.getCode());
                prizeDService.updateById(prizeD);
                prizeD.setMiniType(prize.getMiniType());
                currentRewardId = insertReward(activityId, commonLoginContext, prizeD);
            }
            Prize byId1 = prizeService.findById(Long.valueOf(prizeD.getPrizeId().longValue()));
            decreasePrizeNum(byId1);
            System.out.println("中的奖品"+ prizeD);
                    prizeD.setPrizeId(currentRewardId);
            return prizeD;
        }
        return defaultPrize(commonLoginContext.getUser(), activityId);
    }

    @Override
    public int rollBackStoreById(Integer prizeId) {
        Prize prize = findById(Long.valueOf(prizeId.longValue()));
        Prize updatePrize = Prize.builder()
                .id(prize.getId())
                .givedNum(Integer.valueOf(prize.getGivedNum().intValue() - 1))
                .totalNum(Integer.valueOf(prize.getTotalNum().intValue() + 1))
                .build();
        int i = prizeDao.updateById(updatePrize);
        return i;
    }

    @Override
    public int addchangecodeall(Long activityId) {
        PrizeQuery p = new PrizeQuery();
        p.setActivityId(activityId);
        List<Prize> byCondition = prizeDao.findByCondition(p);
        BigDecimal sumprob = new BigDecimal("100");
        for (Prize prize : byCondition) {
            sumprob = sumprob.subtract(prize.getProb());
        }
        int size = byCondition.size();
        int sizenum = 20 - size;
        BigDecimal sizenum1 = new BigDecimal(sizenum + "");
        BigDecimal divide = sumprob.divide(sizenum1, 0, 4);
        List<CodeType> list = codeTypeService.getCodeTypeForAdd(100);
        if (list == null || list.size() < sizenum);
        int x = 0;
        for (int i = 1; i <= 20; i++) {
            boolean flag = true;
            for (Prize prize : byCondition) {
                if (i == prize.getLevel().intValue()) {
                    flag = false;
                }
            }
            if (flag && list.size() > x) {
                Prize po = new Prize();
                po.setPrizeName(list.get(x).getName());
                po.setProb(divide);
                po.setPrizeType(4);
                po.setLevel(i);
                po.setPrizeIntro(list.get(x).getDes());
                po.setActivityId(activityId);
                po.setTotalNum(100);
                po.setCodeType(list.get(x).getId());
                int id = prizeDao.insertforprize(po);
                PrizeD prizeD = new PrizeD();
                prizeD.setPrizeId(po.getId());
                prizeD.setPrizeName(po.getPrizeName());
                prizeD.setPrizeType(po.getPrizeType());
                prizeD.setProb(po.getProb());
                prizeD.setPrizeIntro(po.getPrizeIntro());
                prizeD.setActivityId(activityId);
                prizeD.setCodeType(po.getCodeType());
                prizeD.setLevel(po.getLevel());
                prizeD.setMiniType(Integer.valueOf(3));
                for (int j = 0; j < 100; j++) {
                    prizeDService.insert(prizeD);
                }
                int b = codeTypeService.desCount(po.getCodeType(), 100);
                if (b == 0) {
                    throw new BizException("修改优惠券库存失败");
                }
                x++;
            }
        }
        return 1;
    }

//    private void insertReward(Long activityId, CommonLoginContext commonLoginContext, PrizeD prizeD2) {
//        Reward reward = Reward.builder()
//                .prizeId(prizeD2.getId())
//                .userId(commonLoginContext.getUserId().intValue())
//                .prizeType(prizeD2.getPrizeType())
//                .prizeName(prizeD2.getName())
//                .activityId(Math.toIntExact(activityId))
//                .build();
//        rewardService.insert(reward);
//    }

    /**
     * 积分中奖处理方法，插入积分明细表，插入中奖表，增加用户积分
     * @param commonLoginContext
     * @param integralPrizeD
     */
    private void integralProcess(CommonLoginContext commonLoginContext, PrizeD integralPrizeD) {
//        Reward reward = Reward.builder()
//                .prizeId(integralPrizeD.getId())
//                .userId(commonLoginContext.getUserId().intValue())
//                .activityId(Math.toIntExact(integralPrizeD.getActivityId()))
//                .prizeType(PrizeEnum.INTEGRAL.getCode())
//                .prizeName(integralPrizeD.getName())
//                .integralNum(integralPrizeD.getIntegralQty())
//                .build();
        Reward reward = Reward.builder()
                .prizeId(integralPrizeD.getId())
                .userId(Integer.valueOf(commonLoginContext.getUserId().intValue()))
                .activityId(Integer.valueOf(Math.toIntExact(integralPrizeD.getActivityId().longValue())))
                .prizeType(PrizeEnum.INTEGRAL.getCode())
                .prizeName(integralPrizeD.getIntegralQty() + "个饭团")
                .integralNum(integralPrizeD.getIntegralQty())
                .build();
        rewardService.insert(reward);
    }

    /**
     * 获取一个默认奖项
     * @return 默认奖
     */
    private PrizeD defaultPrize(User user, Long activityId) {
//        Reward reward = Reward.builder()
//                .prizeId(0)
//                .userId(Math.toIntExact(user.getId()))
//                .activityId(Math.toIntExact(activityId))
//                .prizeType(PrizeEnum.INTEGRAL.getCode())
//                .prizeName("积分")
//                .integralNum(PrizeEnum.DEFAULT_PRIZE.getCode())
//                .build();
        Reward reward = Reward.builder()
                .prizeId(Integer.valueOf(0))
                .userId(Integer.valueOf(Math.toIntExact(user.getId().longValue())))
                .activityId(Integer.valueOf(Math.toIntExact(activityId.longValue())))
                .prizeType(PrizeEnum.INTEGRAL.getCode())
                .prizeName(PrizeEnum.DEFAULT_PRIZE.getCode() + "个饭团")
                .integralNum(PrizeEnum.DEFAULT_PRIZE.getCode()).build();
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

    private Integer insertReward(Long activityId, CommonLoginContext commonLoginContext, PrizeD prizeD2) {
//        Reward reward = Reward.builder()
//                .prizeId(prizeD2.getId())
//                .userId(Integer.valueOf(commonLoginContext.getUserId().intValue()))
//                .prizeType(prizeD2.getPrizeType())
//                .prizeName(prizeD2.getPrizeName())
//                .activityId(Integer.valueOf(Math.toIntExact(activityId.longValue())))
//                .miniType(prizeD2.getMiniType())
//                .keyId(prizeD2.getKeyId())
//                .rewardDescription(prizeD2.getPrizeIntro())
//                .prizedId(prizeD2.getId()).build();
        Reward reward = Reward.builder()
                .prizeId(prizeD2.getId())
                .userId(Integer.valueOf(commonLoginContext.getUserId().intValue()))
                .prizeType(prizeD2.getPrizeType())
                .prizeName(prizeD2.getPrizeName())
                .activityId(Integer.valueOf(Math.toIntExact(activityId.longValue())))
                .miniType(prizeD2.getMiniType()).keyId(prizeD2.getKeyId())
                .rewardDescription(prizeD2.getPrizeIntro())
                .prizedId(prizeD2.getId())
                .build();
        if (prizeD2.getStatus() != null) {
            reward.setStatus(prizeD2.getStatus());
        }
        rewardService.insert(reward);
        return reward.getId();
    }
}
