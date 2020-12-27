package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.InternetResources;
import com.mem.vo.business.base.model.po.InternetResourcesQuery;
import com.mem.vo.common.dto.Page;

import java.util.List;
/**
*
* <br>
* <b>功能：</b>InternetResourcesService<br>
*/
public interface InternetResourcesService {


    /**
    * 添加网络资源表
    * @param  internetResourcesQuery 回复网络资源表实体
    * @return 返回添加的网络资源表的ID
    */
    int insert(InternetResources internetResourcesQuery);

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
    * @param  query 查询网络资源表条件
    * @return 返回查询的集合
    */
    List<InternetResources> findByCondition(InternetResourcesQuery query);

    /**
    * 根据条件查询网络资源表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, InternetResourcesQuery query);

    /**
     * 根据资源类型查询七牛云上信息
     * @param type  资源类型
     * @return  资源列表
     */
    List<InternetResources> findQiniuResources(int type);
}
