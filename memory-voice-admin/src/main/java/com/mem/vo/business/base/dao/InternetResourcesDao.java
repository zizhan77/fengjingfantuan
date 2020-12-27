package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.InternetResources;
import com.mem.vo.business.base.model.po.InternetResourcesQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>InternetResourcesDao<br>
*/
public interface InternetResourcesDao {

    /**
    * 添加网络资源表实体
    * @param  internetResources 回复网络资源表实体
    * @return 返回添加的网络资源表的ID
    */
    int insert(InternetResources internetResources);

    /**
    * 更新网络资源表
    * @param  internetResources 更新网络资源表实体
    * @return 返回更新的网络资源表的ID
    */
    int updateById(InternetResources internetResources);

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
    InternetResources findById(Long id);


    /**
    * 根据条件查询网络资源表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<InternetResources> findByCondition(@Param("condition") InternetResourcesQuery query);

    /**
    * 根据条件查询网络资源表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findByCondition(@Param("page") Page page, @Param("condition") InternetResourcesQuery query);

}
