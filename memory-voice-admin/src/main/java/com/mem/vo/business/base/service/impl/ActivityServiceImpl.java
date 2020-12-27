package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.ActivityDao;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQuery;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.common.dto.Page;
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
    private ActivityDao activityDao;

    @Override
    public int insert(Activity activity) {
        return activityDao.insert(activity);
    }

    @Override
    public int updateById(Activity activity) {
        return activityDao.updateById(activity);
    }

    @Override
    public int deleteById(Long id) {
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

}
