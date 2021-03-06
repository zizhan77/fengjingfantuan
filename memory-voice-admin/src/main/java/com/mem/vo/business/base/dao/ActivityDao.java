package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQuery;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.vo.ActivityVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>ActivityDao<br>
 */
public interface ActivityDao {

    /**
     * 添加接单中台表实体
     *
     * @param activity 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Activity activity);

    /**
     * 更新接单中台表
     *
     * @param activity 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Activity activity);

    /**
     * 删除接单中台表
     *
     * @param id 接单中台表ID
     */
    int deleteById(Long id);

    /**
     * 根据ID查询接单中台表
     *
     * @param id 接单中台表ID
     * @return 返回一条接单中台表
     */
    Activity findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<Activity> findByCondition(@Param("condition") ActivityQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<Activity> findByCondition(@Param("page") Page page, @Param("condition") ActivityQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<Activity> findByConditionAvailable(@Param("condition") ActivityQuery query);

    List<Activity> findByPage(@Param("condition") ActivityQuery query, @Param("page") Page page);

    List<Activity> pcFindByPage(@Param("condition") ActivityQuery query, @Param("page") Page page);

    int findByPageToPhoneCount();

    List<ActivityVO> findByPageToPhone(@Param("paging") PageBean<ActivityVO> paging);

    List<ActivityVO> getActivity(@Param("paging") PageBean<ActivityVO> paging,@Param("name") String name);

    ActivityVO queryDetailByPhone(Integer id);

    Integer querySort(Long paramLong);

    String findSplistToid(Long activityid);

    int queryActivityByUserCount(@Param("type") Integer type, @Param("id") Long id);

    List<ActivityVO> queryActivityByUser(@Param("paging") PageBean<ActivityVO> paging, @Param("type") Integer type, @Param("id") Long id);

    List<Integer> getActivityEnd();

    List<User> getActivityUser(@Param("i") Integer i);

    Activity getActivitySumPass(@Param("a") Activity a);

    Activity getRankSumPass(@Param("a") Activity a);
}
