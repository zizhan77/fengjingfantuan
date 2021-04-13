package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.ActivityUrlDao;
import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityUrl;
import com.mem.vo.business.base.service.ActivityService;
import com.mem.vo.business.base.service.ActivityUrlService;
import com.mem.vo.common.constant.DefaultEnum;
import com.mem.vo.common.constant.StatusEnum;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("activityUrlService")
public class ActivityUrlServiceImpl implements ActivityUrlService {

    @Resource
    private ActivityUrlDao activityUrlDao;

    @Resource
    private ActivityService activityService;

    @Override
    public List<ActivityUrl> query(ActivityUrl activityUrl) {
        return activityUrlDao.query(activityUrl);
    }

    @Override
    public List<ActivityUrl> queryPage(ActivityUrl activityUrl, Page page) {
        return activityUrlDao.queryPage(activityUrl, page);
    }

    @Override
    public ActivityUrl edit(ActivityUrl activityUrl) {
        if (activityUrl.getId() != null && !"".equals(activityUrl.getId())) {
            int j = activityUrlDao.update(activityUrl);
            if (j == 0) {
                throw new BizException("修改失败");
            }
            return activityUrl;
        }
        int i = activityUrlDao.insert(activityUrl);
        if (i == 0) {
            throw new BizException("增加失败");
        }
        return activityUrl;
    }

    @Override
    public ActivityUrl queryOne(String id) {
        Activity byId = activityService.findById(Long.valueOf(id));
        String sponsorId = byId.getSponsorId();
        String[] split = sponsorId.split("~");
        List<Activity> objects = new ArrayList<>();
        List<ActivityUrl> objects1 = new ArrayList<>();
        ActivityUrl activityUrl = new ActivityUrl();
        objects.add(byId);
        for (String s : split) {
            activityUrl.setSponsorId(s);
            activityUrl.setStatus(StatusEnum.ON.getCode() + "");
            List<ActivityUrl> query = activityUrlDao.query(activityUrl);
            if (query.size() != 0) {
                for (ActivityUrl activityQa : query) {
                    objects1.add(activityQa);
                }
            }
        }
        if (objects1.size() == 0) {
            activityUrl.setSponsorId(DefaultEnum.JIYIZHISHENG.getCode() + "");
            List<ActivityUrl> query = activityUrlDao.query(activityUrl);
            for (ActivityUrl activityQa : query) {
                objects1.add(activityQa);
            }
        }
        int index = (int)(Math.random() * (objects1.size() - 1));
        if (objects1.size() == 0) {
            throw new BizException("查询为空");
        }
        ActivityUrl activityUrl1 = objects1.get(index);
        return activityUrl1;
    }

    @Override
    public int deleteById(Long id) {
        return activityUrlDao.deleteById(id);
    }
}
