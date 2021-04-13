package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ActivityUrl;
import com.mem.vo.common.dto.Page;
import java.util.List;

public interface ActivityUrlService {
    List<ActivityUrl> query(ActivityUrl activityUrl);

    List<ActivityUrl> queryPage(ActivityUrl activityUrl, Page page);

    ActivityUrl edit(ActivityUrl activityUrl);

    ActivityUrl queryOne(String id);

    int deleteById(Long id);
}
