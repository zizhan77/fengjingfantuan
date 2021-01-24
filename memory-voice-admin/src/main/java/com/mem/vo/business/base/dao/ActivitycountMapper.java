package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.Activitycount;
import com.mem.vo.business.base.model.vo.ActivitycountVO;
import com.mem.vo.common.dto.Page;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivitycountMapper {
    int insert(Activitycount activitycount);

    int update(Activitycount activitycount);

    List<Activitycount> selectBy(Activitycount activitycount);

    List<ActivitycountVO> query(String paramString, Activitycount activitycount);

    List<ActivitycountVO> queryByName(@Param("page") Page page, String paramString);

    List<ActivitycountVO> getabcd();

    void updateabcd();
}
