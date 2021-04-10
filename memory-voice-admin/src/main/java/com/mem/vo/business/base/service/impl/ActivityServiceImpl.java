package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.*;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQa;
import com.mem.vo.business.base.model.po.ActivityQaQuery;
import com.mem.vo.business.base.model.po.ActivityQuery;
import com.mem.vo.business.base.model.po.ActivityUrl;
import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.PrizeQuery;
import com.mem.vo.business.base.model.po.Ranking;
import com.mem.vo.business.base.model.vo.ActivityVO;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.base.service.CodeTypeService;
import com.mem.vo.business.base.service.PrizeService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.DefaultEnum;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.JsonUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>ActivityService<br>
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    private final static Logger log = LogManager.getLogger(ActivityServiceImpl.class);


    @Resource
    private ActivityUrlDao activityUrlDao;

    @Resource
    private ActivityQaDao activityQaDao;

    @Resource
    private ActivityDao activityDao;

    @Resource
    private PrizeService prizeService;

    @Resource
    private PrizeDao prizeDao;

    @Resource
    private CodeTypeService codeTypeService;

    @Resource
    private RankingDao rankingDao;

    @Override
    public int insert(Activity activity) {

        Ranking rank = new Ranking();
        rank.setCount(activity.getCount());
        rank.setName(activity.getActivityTitle());
        rank.setEnable(activity.getRankstatus() + "");
        rank.setUrl(activity.getThumbnailUrl());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(activity.getRankstart());
            Date dateend = simpleDateFormat.parse(activity.getRankend());
            rank.setStartTime(date);
            rank.setEndTime(dateend);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer add = rankingDao.insert(rank);
        if (add.intValue() == 0) {
            throw new BizException("添加失败");
        }
        activity.setOfranking(rank.getId());
        return activityDao.insert(activity);
    }

    @Override
    public int updateById(Activity activity) {
        return activityDao.updateById(activity);
    }

    @Override
    public int deleteById(Long id) {
//        return activityDao.deleteById(id);
        Activity byId = activityDao.findById(id);
        rankingDao.deleteById(byId.getOfranking());
        PrizeQuery prizeQuery = PrizeQuery.builder().activityId(id).build();
        List<Prize> prizeList = prizeDao.findByCondition(prizeQuery);
        for (Prize prize : prizeList) {
            if (PrizeEnum.coupon.getCode().equals(prize.getPrizeType())) {
                codeTypeService.addCount(prize.getCodeType(), prize.getTotalNum().intValue());
            }
            prizeDao.deleteById(Long.valueOf(prize.getId().intValue()));
        }
        return activityDao.deleteById(id);
    }

    @Override
    public Activity findById(Long id) {
        return activityDao.findById(id);
    }

    @Override
    public List<Activity> findByCondition(ActivityQuery query) {
        return activityDao.findByCondition(query);
    }

    @Override
    public Page<Activity> findPageByCondition(Page page, ActivityQuery query) {
        List<Activity>  list = activityDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Override
    public List<Activity> findByConditionAvailable(ActivityQuery query) {
        if (query == null) {
            query = new ActivityQuery();
        }
        return activityDao.findByConditionAvailable(query);
    }

    @Override
    public Page<Activity> findByPage(ActivityQuery activityQuery, Page page) {
        if (activityQuery == null) {
            activityQuery = new ActivityQuery();
        }
        List<Activity> byPage = activityDao.findByPage(activityQuery, page);
        page.setData(byPage);
        return page;
    }

    @Override
    public List<String> queryQa(String id) {
        String[] split;
        Activity activity = activityDao.findById(Long.valueOf(id));
        String sponsorId = activity.getSponsorId();
        if (sponsorId != null && !"".equals(sponsorId)) {
            split = sponsorId.split("~");
        } else {
            split = new String[] { DefaultEnum.JIYIZHISHENG.getCode() + "" };
        }
        List<String> strings = new ArrayList<>();
        for (String s : split) {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            ActivityQaQuery activityQaQuery = new ActivityQaQuery();
            activityQaQuery.setSponsorId(Integer.valueOf(Integer.parseInt(s + "")));
            ActivityUrl activityUrl = new ActivityUrl();
            activityUrl.setSponsorId(s + "");
            for (int i = 0; i < 3; i++) {
                int points = (int)(1.0D + Math.random() * 1.0D);
                if (points == 1) {
                    List<ActivityQa> byCondition = activityQaDao.findByCondition(activityQaQuery);
                    int index = (int)(Math.random() * (byCondition.size() - 1));
                    ActivityQa activityQa = byCondition.get(index);
                    String str = JsonUtil.toJson(activityQa);
                    strings.add(str);
                }
                if (points == 2) {
                    List<ActivityUrl> list = activityUrlDao.query(activityUrl);
                    int index = (int)(Math.random() * (list.size() - 1));
                    ActivityUrl activityUrl1 = list.get(index);
                    String str = JsonUtil.toJson(activityUrl1);
                    strings.add(str);
                }
            }
        }
        return strings;
    }

    @Override
    public Prize queryPrizeById(String activityId) {
        BizAssert.notNull(activityId, BizCode.PARAM_NULL.getMessage());
        PrizeQuery build = PrizeQuery.builder().activityId(Long.valueOf(activityId)).prizeType(PrizeEnum.TICKET.getCode()).build();
        List<Prize> byCondition = prizeService.findByCondition(build);
        BizAssert.notEmpty(byCondition, BizCode.BIZ_1102.getMessage());
        return byCondition.get(0);
    }

    @Override
    public Page<Activity> pcFindByPage(ActivityQuery activityQuery, Page<Activity> page) {
        if (activityQuery == null) {
            activityQuery = new ActivityQuery();
        }
        List<Activity> byPage = activityDao.pcFindByPage(activityQuery, page);
        for (Activity activity : byPage) {
            Activity a = activityDao.getActivitySumPass(activity);
            if (a != null) {
                activity.setPeoplenum(a.getPeoplenum());
                activity.setNumclick(a.getNumclick());
            }
            Activity b = activityDao.getRankSumPass(activity);
            if (b != null) {
                activity.setRankprople(b.getRankprople());
                activity.setRanknum(b.getRanknum());
            }
        }
        page.setData(byPage);
        return page;
    }

    @Override
    public ActivityVO queryDetailByPhone(Integer id) {
        ActivityVO act = activityDao.queryDetailByPhone(id);
        ActivityVO a = prizeDao.gettotalandgived(act.getId());
        if (a != null) {
            act.setTotalNum(a.getTotalNum());
            act.setGivedNum(a.getGivedNum());
        }
        return act;
    }

    @Override
    public PageBean<ActivityVO> findByPageToPhone(Integer pageNo, Integer pageSize) {
        int Commod = activityDao.findByPageToPhoneCount();
        PageBean<ActivityVO> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<ActivityVO> list = activityDao.findByPageToPhone(pager);
        for (ActivityVO act : list) {
            ActivityVO a = prizeDao.gettotalandgived(act.getId());
            if (a != null) {
                act.setTotalNum(a.getTotalNum());
                act.setGivedNum(a.getGivedNum());
            }
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<ActivityVO> getActivity(Integer pageNo, Integer pageSize, String name) {
        int Commod = activityDao.findByPageToPhoneCount();
        PageBean<ActivityVO> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<ActivityVO> list = activityDao.getActivity(pager, name);
        for (ActivityVO act : list) {
            ActivityVO a = prizeDao.gettotalandgived(act.getId());
            if (a != null) {
                act.setTotalNum(a.getTotalNum());
                act.setGivedNum(a.getGivedNum());
            }
        }
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<ActivityVO> queryActivityByUser(Integer pageNo, Integer pageSize, Integer flag, Long userId) {
        int Commod = activityDao.queryActivityByUserCount(flag, userId);
        PageBean<ActivityVO> pager = new PageBean();
        pager.setPageSize(pageSize);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageNo(pageNo);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<ActivityVO> list = activityDao.queryActivityByUser(pager, flag, userId);
        for (ActivityVO act : list) {
            ActivityVO a = prizeDao.gettotalandgived(act.getId());
            if (a != null) {
                act.setTotalNum(a.getTotalNum());
                act.setGivedNum(a.getGivedNum());
            }
        }
        pager.setLists(list);
        return pager;
    }

}
