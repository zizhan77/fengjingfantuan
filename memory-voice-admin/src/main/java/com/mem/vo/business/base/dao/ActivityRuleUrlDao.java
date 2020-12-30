package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ActivityRuleUrl;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ActivityRuleUrlDao {

    @Update({"update  activityRuleUrl  set url= #{url} where id= #{id}"})
    int updateById(@Param("id") Integer paramInteger, @Param("url") String paramString);

    @Insert({"insert into activityRuleUrl (url) VALUES (#{url}) "})
    int insert(@Param("url") String paramString);

    @Select({"select * from  activityRuleUrl"})
    List<ActivityRuleUrl> query();
}
