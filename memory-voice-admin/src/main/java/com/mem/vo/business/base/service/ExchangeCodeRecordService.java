package com.mem.vo.business.base.service;


import com.mem.vo.business.base.model.po.ExchangeCodeRecord;
import com.mem.vo.business.base.model.po.ExchangeCodeRecordQuery;
import com.mem.vo.common.dto.Page;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>ExchangeCodeRecordService<br>
*/
public interface ExchangeCodeRecordService {


    /**
    * 添加兑换码表
    * @param  exchangeCodeRecordQuery 回复兑换码表实体
    * @return 返回添加的兑换码表的ID
    */
    int insert(ExchangeCodeRecord exchangeCodeRecordQuery);

    /**
    * 更新兑换码表
    * @param  exchangeCodeRecord 更新兑换码表实体
    * @return 返回更新的兑换码表的ID
    */
    int updateById(ExchangeCodeRecord exchangeCodeRecord);

    /**
    * 删除兑换码表
    * @param  id 兑换码表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询兑换码表
    * @param  id 兑换码表ID
    * @return 返回一条兑换码表
    */
    ExchangeCodeRecord findById(Long id);

    /**
    * 根据条件查询兑换码表
    * @param  query 查询兑换码表条件
    * @return 返回查询的集合
    */
    List<ExchangeCodeRecord> findByCondition(ExchangeCodeRecordQuery query);

    /**
    * 根据条件查询兑换码表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    Page<ExchangeCodeRecord> findPageByCondition(Page page, ExchangeCodeRecordQuery query);

    int updateByExchangeCode(String exchangeCode,String oldStatus,String status);
}
