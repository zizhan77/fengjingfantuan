package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.Activitycount;
import com.mem.vo.business.base.model.vo.ActivitycountVO;
import com.mem.vo.common.dto.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivitycountMapper {
    int insert(Activitycount paramActivitycount);

    int update(Activitycount paramActivitycount);

    List<Activitycount> selectBy(Activitycount paramActivitycount);

    List<ActivitycountVO> query(String paramString, Activitycount paramActivitycount);

    List<ActivitycountVO> queryByName(@Param("page") Page paramPage, String paramString);

    List<ActivitycountVO> getabcd();

    void updateabcd();
}
