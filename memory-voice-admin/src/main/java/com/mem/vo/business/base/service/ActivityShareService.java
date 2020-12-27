package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ActivityShare;
import com.mem.vo.business.base.model.po.ActivityShareQuery;
import com.mem.vo.common.dto.Page;

import java.util.Date;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>ActivityShareService<br>
*/
public interface ActivityShareService {


    /**
    * 添加网络资源表
    * @param  activityShareQuery 回复网络资源表实体
    * @return 返回添加的网络资源表的ID
    */
    int insert(ActivityShare activityShareQuery);

    /**
    * 更新网络资源表
    * @param  activityShare 更新网络资源表实体
    * @return 返回更新的网络资源表的ID
    */
    int updateById(ActivityShare activityShare);

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
    ActivityShare findById(Long id);

    /**
    * 根据条件查询网络资源表
    * @param  query 查询网络资源表条件
    * @return 返回查询的集合
    */
    List<ActivityShare> findByCondition(ActivityShareQuery query);

    /**
    * 根据条件查询网络资源表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, ActivityShareQuery query);

    /**
     * 更改可抽奖次数
     * @param shareId   分享者业务ID
     * @return  返回可抽奖次数
     */
    Integer updateLotteryQty(String token,Long shareId, Integer tye,Integer activityId);

    /**
     * 获取用户可抽奖次数
     * @param token token信息
     * @return  返回可抽奖次数
     */
    Integer queryLotteryQtyByToken(String token, Integer activityId);
}
