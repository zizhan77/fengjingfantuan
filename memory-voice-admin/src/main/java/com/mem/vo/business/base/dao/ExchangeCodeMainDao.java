package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ExchangeCodeMain;
import com.mem.vo.business.base.model.po.ExchangeCodeMainQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>ExchangeCodeMainDao<br>
*/
public interface ExchangeCodeMainDao {

    /**
    * 添加兑换码主表实体
    * @param  exchangeCodeMain 回复兑换码主表实体
    * @return 返回添加的兑换码主表的ID
    */
    int insert(ExchangeCodeMain exchangeCodeMain);

    /**
    * 更新兑换码主表
    * @param  exchangeCodeMain 更新兑换码主表实体
    * @return 返回更新的兑换码主表的ID
    */
    int updateById(ExchangeCodeMain exchangeCodeMain);

    /**
    * 删除兑换码主表
    * @param  id 兑换码主表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询兑换码主表
    * @param  id 兑换码主表ID
    * @return 返回一条兑换码主表
    */
    ExchangeCodeMain findById(Long id);


    /**
    * 根据条件查询兑换码主表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<ExchangeCodeMain> findByCondition(@Param("condition") ExchangeCodeMainQuery query);

    /**
    * 根据条件查询兑换码主表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    List<ExchangeCodeMain> findByCondition(@Param("page") Page page, @Param("condition") ExchangeCodeMainQuery query);

    int updateUsedNumById(@Param("id") Long id);

}
