package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ActivityQaDao;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQa;
import com.mem.vo.business.base.model.po.ActivityQaQuery;
import com.mem.vo.business.base.service.ActivityQaService;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.DefaultEnum;
import com.mem.vo.common.constant.StatusEnum;
import com.mem.vo.common.dto.Page;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
*
* <br>
* <b>功能：</b>ActivityQaService<br>
*/
@Service("activityQaService")
public class  ActivityQaServiceImpl implements ActivityQaService {
    private final static Logger log= LogManager.getLogger(ActivityQaServiceImpl.class);


    @Resource
    private ActivityQaDao activityQaDao;

    @Resource
    private TokenService tokenService;

    @Resource
    private ActivityService activityService;

    @Override
    public int insert(ActivityQa activityQa){
        return activityQaDao.insert(activityQa);
    }

    @Override
    public int updateById(ActivityQa activityQa){
        return activityQaDao.updateById(activityQa);
    }

    @Override
    public int deleteById(List<Long> ids){
        return activityQaDao.deleteById(ids);
    }

    @Override
    public ActivityQa findById(Long id){
        return activityQaDao.findById(id);
    }

    @Override
    public Page<ActivityQa> findByCondition(ActivityQaQuery query, Page page) {
        List<ActivityQa> a = this.activityQaDao.findByCondition(page, query);
        page.setData(a);
        return page;
    }

//    @Override
//    public List<ActivityQa> findByCondition(ActivityQaQuery query){
//        return activityQaDao.findByCondition(query);
//    }

    @Override
    public void findPageByCondition(Page page, ActivityQaQuery query){
        activityQaDao.findByCondition(page,query);
    }

    @Override
    public Integer inserts(String token, List<ActivityQa> activityQas) {
        CommonLoginContext contextByken = this.tokenService.getContextByken(token);
        Long spsonerId = contextByken.getUserId();
        for (ActivityQa activityQa : activityQas) {
            activityQa.setSponsorId(Integer.valueOf(spsonerId.intValue()));
        }
        return this.activityQaDao.inserts(activityQas);
    }

    @Override
    public ActivityQa queryOne(Long id) {
        System.out.println(id);
        Activity byId = this.activityService.findById(Long.valueOf(id.longValue()));
        String sponsorId = byId.getSponsorId();
        String[] split = sponsorId.split("~");
        List<Activity> objects = new ArrayList<>();
        List<ActivityQa> objects1 = new ArrayList<>();
        ActivityQaQuery activityQaQuery1 = new ActivityQaQuery();
        objects.add(byId);
        for (String s : split) {
            activityQaQuery1.setSponsorId(Integer.valueOf(Integer.parseInt(s)));
            activityQaQuery1.setStatus(StatusEnum.ON.getCode());
            List<ActivityQa> byCondition = activityQaDao.findByCondition(activityQaQuery1);
            if (byCondition.size() != 0) {
                for (ActivityQa activityQa1 : byCondition) {
                    objects1.add(activityQa1);
                }
            }
        }
        if (objects1.size() == 0) {
            activityQaQuery1.setSponsorId(DefaultEnum.JIYIZHISHENG.getCode());
            List<ActivityQa> byCondition = activityQaDao.findByCondition(activityQaQuery1);
            for (ActivityQa activityQa1 : byCondition) {
                objects1.add(activityQa1);
            }
        }
        int index = (int)(Math.random() * (objects1.size() - 1));
        ActivityQa activityQa = objects1.get(index);
        return activityQa;
    }
}
