package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Activitycount;
import com.mem.vo.business.base.model.vo.ActivitycountVO;
import com.mem.vo.common.dto.Page;
import java.util.List;

public interface ActivityCountService {
    String add(String paramString1, String paramString2);

    List<ActivitycountVO> query(String paramString, Activitycount paramActivitycount);

    Page<ActivitycountVO> queryByName(Page<ActivitycountVO>Page, String paramString);

    void a();
}
