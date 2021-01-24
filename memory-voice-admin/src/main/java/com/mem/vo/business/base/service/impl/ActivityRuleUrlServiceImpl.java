package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ActivityRuleUrlDao;
import com.mem.vo.business.base.model.po.ActivityRuleUrl;
import com.mem.vo.business.base.service.ActivityRuleUrlService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("ActivityRuleUrlService")
public class ActivityRuleUrlServiceImpl implements ActivityRuleUrlService {
    @Resource
    private ActivityRuleUrlDao activityRuleUrlDao;

    @Override
    public ActivityRuleUrl updateById(ActivityRuleUrl activity) {
        if (activity.getId() != null && !"".equals(activity.getId())) {
            int j = activityRuleUrlDao.updateById(activity.getId(), activity.getUrl());
            if (j == 0) {
                throw new BizException("修改活动规则图片异常");
            }
            return activity;
        }
        int i = this.activityRuleUrlDao.insert(activity.getUrl());
        if (i == 0) {
            throw new BizException("增加活动规则图片异常");
        }
        return activity;
    }

    @Override
    public List<ActivityRuleUrl> query() {
        List<ActivityRuleUrl> list = activityRuleUrlDao.query();
        BizAssert.notEmpty(list, BizCode.BIZ_1102.getMessage());
        return list;
    }
}
