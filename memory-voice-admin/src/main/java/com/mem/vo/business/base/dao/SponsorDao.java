package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.SponsorQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>SponsorDao<br>
*/
public interface SponsorDao {

    /**
    * 添加用户分享表实体
    * @param  sponsor 回复用户分享表实体
    * @return 返回添加的用户分享表的ID
    */
    int insert(Sponsor sponsor);

    /**
    * 更新用户分享表
    * @param  sponsor 更新用户分享表实体
    * @return 返回更新的用户分享表的ID
    */
    int updateById(Sponsor sponsor);

    /**
    * 删除用户分享表
    * @param  ids 用户分享表ID
    */
    int deleteById(List<Long> ids);

    /**
    * 根据ID查询用户分享表
    * @param  id 用户分享表ID
    * @return 返回一条用户分享表
    */
    Sponsor findById(Long id);


    /**
    * 根据条件查询用户分享表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<Sponsor> findByCondition(@Param("condition") SponsorQuery query);

    /**
    * 根据条件查询用户分享表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findByCondition(@Param("page") Page page, @Param("condition") SponsorQuery query);

    List<Sponsor> findByConditionbyishow();

    Sponsor getSponsorOne(Long paramLong);

    List<String> getSponsorPrize(Long paramLong);
}
