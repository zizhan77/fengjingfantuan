package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ActivityUser;
import com.mem.vo.business.base.model.po.ActivityUserQuery;
import com.mem.vo.business.base.model.po.ActivitysharePc;
import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
//    int updateByUserIdAndActivityId(Integer qty,String id,Integer acId);
    int updateByUserIdAndActivityId(Integer passQty, String userId, Integer activityId, String date, Integer lotteryQty);

    @Select({"SELECT COUNT(1) FROM `activity_user` WHERE `pass_qty` = 12"})
    int findCountPass();

    @Update({"UPDATE activity_user set lottery_qty=(lottery_qty+1) , is_share=1 where user_id = #{bizCode} and activity_id=#{activityId}"})
    Integer updateLotteryQt(String paramString1, String paramString2);

    @Select({"SELECT * from activity_user where user_id = #{userId} and activity_id = #{activityId}  and pass_qty =#{code} and  date_format(update_time,'%e %b %y') = date_format(now(), '%e %b %y');"})
    List<ActivityUser> findTodayByUserIdAndActivityId(@Param("userId") String paramString, @Param("activityId") Integer paramInteger1, @Param("code") Integer paramInteger2);

    int queryShareAddUserListCount(@Param("act") ActivitysharePc paramActivitysharePc);

    List<ActivitysharePc> queryShareAddUserList(@Param("act") ActivitysharePc paramActivitysharePc, @Param("pager") PageBean paramPageBean);

    int queryRewardByActivityCount(@Param("id") Integer paramInteger);

    List<Prize> queryRewardByActivity(@Param("paging") PageBean<Prize> paramPageBean, @Param("id") Integer paramInteger);

    int queryRewardByActivityAndUserCount(@Param("id") Integer paramInteger, @Param("userid") Long paramLong);

    List<Prize> queryRewardByActivityAndUser(@Param("paging") PageBean<Prize> paramPageBean, @Param("id") Integer paramInteger, @Param("userid") Long paramLong);

    int queryShareUserbyUserCount(@Param("id") Integer paramInteger, @Param("userid") Long paramLong);

    List<User> queryShareUserbyUser(@Param("paging") PageBean<User> paramPageBean, @Param("id") Integer paramInteger, @Param("userid") Long paramLong);

}
