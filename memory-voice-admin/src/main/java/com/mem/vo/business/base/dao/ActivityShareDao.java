package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ActivityShare;
import com.mem.vo.business.base.model.po.ActivityShareQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>ActivityShareDao<br>
*/
public interface ActivityShareDao {

    /**
    * 添加用户分享表实体
    * @param  activityShare 回复用户分享表实体
    * @return 返回添加的用户分享表的ID
    */
    int insert(ActivityShare activityShare);

    /**
    * 更新用户分享表
    * @param  activityShare 更新用户分享表实体
    * @return 返回更新的用户分享表的ID
    */
    int updateById(ActivityShare activityShare);

    /**
    * 删除用户分享表
    * @param  id 用户分享表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询用户分享表
    * @param  id 用户分享表ID
    * @return 返回一条用户分享表
    */
    ActivityShare findById(Long id);


    /**
    * 根据条件查询用户分享表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<ActivityShare> findByCondition(@Param("condition") ActivityShareQuery query);

    /**
    * 根据条件查询用户分享表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findByCondition(@Param("page") Page page, @Param("condition") ActivityShareQuery query);

    /**
     * 根据条件查询用户分享表(今日)
     * @param  query 查询条件
     * @return 返回查询的集合
     */
    List<ActivityShare> findByConditionToday(@Param("condition") ActivityShareQuery query);

    /**
     * 根据条件查询用户分享条数表(今日)
     * @param  query 查询条件
     * @return 返回查询的集合
     */
    Integer findCountByConditionToday(@Param("condition") ActivityShareQuery query);

    @Select("select * from activity_share where is_delete = 0 ")
    List<ActivityShare> findAll();
}
