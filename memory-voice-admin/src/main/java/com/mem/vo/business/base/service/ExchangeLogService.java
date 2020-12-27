package com.mem.vo.business.base.service;


import com.mem.vo.business.base.model.po.ExchangeLog;
import com.mem.vo.business.base.model.po.ExchangeLogQuery;
import com.mem.vo.common.dto.Page;

import java.util.List;
/**
*
* <br>
* <b>功能：</b>ExchangeLogService<br>
*/
public interface ExchangeLogService {


    /**
    * 添加兑换日志表
    * @param  exchangeLogQuery 回复兑换日志表实体
    * @return 返回添加的兑换日志表的ID
    */
    int insert(ExchangeLog exchangeLogQuery);

    /**
    * 更新兑换日志表
    * @param  exchangeLog 更新兑换日志表实体
    * @return 返回更新的兑换日志表的ID
    */
    int updateById(ExchangeLog exchangeLog);

    /**
    * 删除兑换日志表
    * @param  id 兑换日志表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询兑换日志表
    * @param  id 兑换日志表ID
    * @return 返回一条兑换日志表
    */
    ExchangeLog findById(Long id);

    /**
    * 根据条件查询兑换日志表
    * @param  query 查询兑换日志表条件
    * @return 返回查询的集合
    */
    List<ExchangeLog> findByCondition(ExchangeLogQuery query);

    /**
    * 根据条件查询兑换日志表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    Page<ExchangeLog> findPageByCondition(Page page, ExchangeLogQuery query);
}
