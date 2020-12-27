package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ActivityUser;
import com.mem.vo.business.base.model.po.ActivityUserQuery;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>ActivityUserService<br>
*/
public interface ActivityUserService {


    /**
    * 添加网络资源表
    * @param  activityUserQuery 回复网络资源表实体
    * @return 返回添加的网络资源表的ID
    */
    int insert(ActivityUser activityUserQuery);

    /**
    * 更新网络资源表
    * @param  activityUser 更新网络资源表实体
    * @return 返回更新的网络资源表的ID
    */
    int updateById(ActivityUser activityUser);

    /**
    * 删除网络资源表
    * @param  id 网络资源表ID
    */
    int deleteById(Long id);


    /**
    * 根据条件查询网络资源表
    * @param  query 查询网络资源表条件
    * @return 返回查询的集合
    */
    List<ActivityUser> findByCondition(ActivityUserQuery query);

    int updateUserPassQty(ActivityUser activityUser);

    int updateLotteryQtyReduceById(long id);
}
