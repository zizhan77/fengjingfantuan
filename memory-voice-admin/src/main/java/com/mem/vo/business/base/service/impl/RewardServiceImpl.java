package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.RewardDao;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.IntegralRoleClass;
import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.model.vo.RewardQueryDto;
import com.mem.vo.business.base.model.vo.RewardVO;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.base.service.IntegralService;
import com.mem.vo.business.base.service.RewardService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.ActivityEnum;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.util.DateUtil;
import com.mem.vo.common.util.ExcelPutUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br>
 * <b>功能：</b>RewardService<br>
 */
@Service("rewardService")
public class RewardServiceImpl implements RewardService {
    private final static Logger log = LogManager.getLogger(RewardServiceImpl.class);


    @Resource
    private TokenService tokenService;

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
//        if (prizeD.getPrizeType() != null && prizeD.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
//            String activityName = "";
//            if (prizeD.getActivityId().equals(ActivityEnum.ACTIVITY_USER_INFO.getCode())) {
//                activityName = ActivityEnum.ACTIVITY_USER_INFO.getName();
//            } else {
//                Activity activity = activityService.findById(Long.valueOf(prizeD.getActivityId()));
//                activityName = activity == null ? "" : activity.getActivityTitle();
//            }
//
//            Integral integralQuery = Integral.builder()
//                    .userId(prizeD.getUserId())
//                    .integralQty(prizeD.getIntegralNum())
//                    .activityId(prizeD.getActivityId())
//                    .activityName(activityName)
//                    .type(0)
//                    .build();
//            integralService.insert(integralQuery);
//        }
//        return rewardDao.insert(prizeD);
        if (prizeD.getPrizeType() != null && prizeD.getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
            String activityName = "";
            if (prizeD.getActivityId().equals(ActivityEnum.ACTIVITY_USER_INFO.getCode())) {
                activityName = ActivityEnum.ACTIVITY_USER_INFO.getName();
            } else {
                Activity activity = activityService.findById(Long.valueOf(prizeD.getActivityId().intValue()));
                activityName = (activity == null) ? "" : activity.getActivityTitle();
            }
            Integral integralQuery = Integral.builder()
                    .userId(prizeD.getUserId())
                    .integralQty(prizeD.getIntegralNum())
                    .activityId(prizeD.getActivityId())
                    .activityName(activityName)
                    .type(Integer.valueOf(0)).build();
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

    @Override
    public List<RewardVO> findBy(String token) {
        CommonLoginContext contextByken = tokenService.getContextByken(token);
        Long userId = null;
        if (contextByken.getSourceCode().equals(SourceType.WX_MINI.getCode())) {
            userId = contextByken.getUserId();
        }
        return rewardDao.findByUserId(contextByken.getUserId());
    }

    @Override
    public List<Reward> findByConditionAndDate(RewardQuery reward) {
        return rewardDao.findByConditionAndDate(reward);
    }

    @Override
    public int updateByPrizedId(Integer prizedId, Integer code) {
        return rewardDao.updateByPrizedId(prizedId, code);
    }

    @Override
    public Integer getUserDefaultIntegral() {
        return rewardDao.getUserDefaultIntegral();
    }

    @Override
    public List<RewardVO> findByActivityId(Integer activityId) {
        return rewardDao.findByActivityId(activityId);
    }

    @Override
    public List<Reward> findByConditionAndCurrentDay(RewardQuery reward) {
        return rewardDao.findByConditionAndCurrentDay(reward);
    }

    @Override
    public PageBean<RewardVO> queryListBy(RewardQueryDto dto) {
        int Commod = rewardDao.queryListByCount(dto);
        PageBean<RewardVO> pager = new PageBean();
        pager.setPageSize(Integer.valueOf((dto.getPageSize() == null) ? 15 : dto.getPageSize().intValue()));
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(Integer.valueOf((dto.getPage() == null) ? 1 : dto.getPage().intValue()));
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<RewardVO> list = rewardDao.queryListBy(pager, dto);
        pager.setLists(list);
        return pager;
    }

    @Override
    public void downLoadExcel(RewardQueryDto dto) {
        Map<String, List<String>> map = new HashMap<>();
        PageBean<RewardVO> pager = new PageBean();
        try {
            pager.setStart(Integer.valueOf(0));
            pager.setPageSize(Integer.valueOf(500));
            List<RewardVO> list = rewardDao.queryListBy(pager, dto);
            String[] strArray = {
                    "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                    "1", "1", "1", "1", "1", "1", "1" };
            for (int i = 0; i < list.size(); i++) {
                ArrayList<String> members = new ArrayList<>();
                members.add((list.get(i)).getId());
                members.add((list.get(i)).getPrizedId() + "");
                members.add((list.get(i)).getActivityName());
                members.add((list.get(i)).getContactPhone());
                members.add((list.get(i)).getIsChange());
                members.add((list.get(i)).getKey());
                members.add((list.get(i)).getPassword());
                members.add((list.get(i)).getPrizeName());
                members.add((list.get(i)).getContactProvince());
                members.add((list.get(i)).getContactAddress());
                members.add((list.get(i)).getStatus());
                members.add((list.get(i)).getTime());
                members.add((list.get(i)).getMiniType() + "");
                members.add((list.get(i)).getPrizeType());
                members.add((list.get(i)).getKeyId() + "");
                members.add((list.get(i)).getUrl());
                members.add((list.get(i)).getDes());
                members.add((list.get(i)).getCodeDes());
                map.put((list.get(i)).getId() + "", members);
            }
            ExcelPutUtil.createExcel(map, strArray);
        } catch (Exception e) {
            log.debug("excel 导出有问题", e.getMessage());
        }
    }

    @Override
    public List<IntegralRoleClass> integralRoleList(Integer id) {
        return rewardDao.integralRoleList(id);
    }

    @Override
    public Integer updateByIdToIntegralRole(IntegralRoleClass roleClass) {
        return rewardDao.updateByIdToIntegralRole(roleClass);
    }

    @Override
    public PageBean<RewardVO> queryListByUser(Long userId, Integer flag, Integer pageNo, Integer pageSize) throws Exception {
        int Commod = rewardDao.queryListByUserCount(userId, flag);
        PageBean<RewardVO> pager = new PageBean();
        pager.setPageSize(Integer.valueOf((pageSize == null) ? 15 : pageSize.intValue()));
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(Integer.valueOf((pageNo == null) ? 1 : pageNo.intValue()));
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<RewardVO> list = new ArrayList<>();
        if (flag.toString().equals("1") || flag.toString().equals("2")) {
            list = rewardDao.queryListByUser(pager, userId, flag);
            for (RewardVO r : list) {
                if (r.getStatus().equals("0")) {
                    String nowStrFile = DateUtil.getNowStrLong();
                    boolean b = DateUtil.judgmentDate(r.getTime(), nowStrFile);
                    if (!b) {
                        Reward ra = new Reward();
                        ra.setId(Integer.valueOf(Integer.parseInt(r.getId())));
                        ra.setStatus(Integer.valueOf(2));
                        updateById(ra);
                        r.setStatus("2");
                    }
                }
            }
        } else {
            list = rewardDao.queryYouhuiquan(pager, userId, flag);
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public int getOldUserIntegral() {
        List<IntegralRoleClass> list = this.rewardDao.integralRoleList(Integer.valueOf(3));
        return list.get(0).getAddcount().intValue();
    }

    @Override
    public int getNewUserIntegral() {
        return rewardDao.getNewUserIntegral();
    }

    @Override
    public IntegralRoleClass getSignlaxin() {
        List<IntegralRoleClass> list = rewardDao.integralRoleList(Integer.valueOf(4));
        return list.get(0);
    }

    @Override
    public Integer updateRewardFlag(String token, Reward reward) {
        reward.setStatus(Integer.valueOf(1));
        return Integer.valueOf(rewardDao.updateById(reward));
    }

    public IntegralRoleClass getSignInIntegralRole() {
        List<IntegralRoleClass> list = rewardDao.integralRoleList(Integer.valueOf(2));
        return list.get(0);
    }

    public Integer getUserIntegralFlagCount() {
        return this.rewardDao.getUserIntegralFlagCount();
    }
}
