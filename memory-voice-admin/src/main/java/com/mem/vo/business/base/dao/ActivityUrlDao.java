package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ActivityUrl;
import com.mem.vo.common.dto.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityUrlDao {
    List<ActivityUrl> query(ActivityUrl paramActivityUrl);

    List<ActivityUrl> queryPage(@Param("activityUrl") ActivityUrl paramActivityUrl, @Param("page") Page paramPage);

    int insert(ActivityUrl paramActivityUrl);

    int update(ActivityUrl paramActivityUrl);

    int deleteById(Long id);
}
