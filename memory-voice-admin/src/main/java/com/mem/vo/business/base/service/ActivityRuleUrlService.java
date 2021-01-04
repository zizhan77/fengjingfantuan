package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ActivityRuleUrl;
import java.util.List;

public interface ActivityRuleUrlService {
    ActivityRuleUrl updateById(ActivityRuleUrl paramActivityRuleUrl);

    List<ActivityRuleUrl> query();
}
