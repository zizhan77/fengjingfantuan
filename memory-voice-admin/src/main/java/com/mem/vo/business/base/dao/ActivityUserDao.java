package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.ActivityUser;
import com.mem.vo.business.base.model.po.ActivityUserQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
*
* <br>
* <b>功能：</b>ActivityUserDao<br>
*/
public interface ActivityUserDao {

    /**
    * 添加活动参加用户表实体
    * @param  activityUser 回复活动参加用户表实体
    * @return 返回添加的活动参加用户表的ID
    */
    int insert(ActivityUser activityUser);

    /**
    * 更新活动参加用户表
    * @param  activityUser 更新活动参加用户表实体
    * @return 返回更新的活动参加用户表的ID
    */
    int updateById(ActivityUser activityUser);
    int updateByUserId(ActivityUser activityUser);

    /**
    * 删除活动参加用户表
    * @param  id 活动参加用户表ID
    */
    int deleteById(Long id);

    /**
    * 根据条件查询活动参加用户表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<ActivityUser> findByCondition(@Param("condition") ActivityUserQuery query);

    /**
    * 根据条件查询活动参加用户表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    List<ActivityUser> findByCondition(@Param("page") Page page, @Param("condition") ActivityUserQuery query);

    /**
     * 根据条件查询数量
     * @param query 查询条件
     * @return  查询结果集
     */
    Integer findCountByCondition(@Param("condition") ActivityUserQuery query);

    int updateLotteryQtyReduceById(long id);
    //@Update( "update activity_user set  pass_qty=? where user_id=? and is_delete = 0 and activity_id=?")
    int updateByUserIdAndActivityId(Integer qty,String id,Integer acId);
}
