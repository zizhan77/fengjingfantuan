package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ActivityQa;
import com.mem.vo.business.base.model.po.ActivityQaQuery;
import com.mem.vo.common.dto.Page;

import java.util.Date;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>ActivityQaService<br>
*/
public interface ActivityQaService {


    /**
    * 添加网络资源表
    * @param  activityQaQuery 回复网络资源表实体
    * @return 返回添加的网络资源表的ID
    */
    int insert(ActivityQa activityQaQuery);

    /**
    * 更新网络资源表
    * @param  activityQa 更新网络资源表实体
    * @return 返回更新的网络资源表的ID
    */
    int updateById(ActivityQa activityQa);

    /**
    * 删除网络资源表
    * @param  id 网络资源表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询网络资源表
    * @param  id 网络资源表ID
    * @return 返回一条网络资源表
    */
    ActivityQa findById(Long id);

    /**
    * 根据条件查询网络资源表
    * @param  query 查询网络资源表条件
     * @param page
    * @return 返回查询的集合
    */
    Page<ActivityQa> findByCondition(ActivityQaQuery query, Page page);
//    List<ActivityQa> findByCondition(ActivityQaQuery query);


    /**
    * 根据条件查询网络资源表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, ActivityQaQuery query);

    Integer inserts(String paramString, List<ActivityQa> paramList);

    ActivityQa queryOne(Long paramLong);
}
