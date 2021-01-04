package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Activity;
import com.mem.vo.business.base.model.po.ActivityQuery;
import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.vo.ActivityVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>ActivityService<br>
 */
public interface ActivityService {


    /**
     * 添加接单中台表
     *
     * @param activityQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Activity activityQuery);

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
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<Activity> findByCondition(ActivityQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<Activity> findPageByCondition(Page page, ActivityQuery query);

    /**
     * 查询未结束的活动列表，可以传入一个空对象
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<Activity> findByConditionAvailable(ActivityQuery query);

    Page<Activity> findByPage(ActivityQuery activityQuery, Page page);

    List<String> queryQa(String id);

    Prize queryPrizeById(String activityId);

    Page<Activity> pcFindByPage(ActivityQuery activityQuery, Page<Activity> page);

    ActivityVO queryDetailByPhone(Integer id);

    PageBean<ActivityVO> findByPageToPhone(Integer pageNo, Integer pageSize);

    PageBean<ActivityVO> queryActivityByUser(Integer pageNo, Integer pageSize, Integer flag, Long userId);

}
