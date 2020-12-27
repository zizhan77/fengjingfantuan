package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ExchangeCodeMain;
import com.mem.vo.business.base.model.po.ExchangeCodeMainQuery;
import com.mem.vo.business.biz.model.vo.exchangecode.ExchangeCodeRequest;
import com.mem.vo.common.dto.Page;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>ExchangeCodeMainService<br>
*/
public interface ExchangeCodeMainService {


    /**
    * 添加兑换码主表
    * @param  exchangeCodeMainQuery 回复兑换码主表实体
    * @return 返回添加的兑换码主表的ID
    */
    int insert(ExchangeCodeMain exchangeCodeMainQuery);

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
    * @param  query 查询兑换码主表条件
    * @return 返回查询的集合
    */
    List<ExchangeCodeMain> findByCondition(ExchangeCodeMainQuery query);

    /**
    * 根据条件查询兑换码主表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    Page<ExchangeCodeMain> findPageByCondition(Page page, ExchangeCodeMainQuery query);

    void testTrans();

    List<String> generateCommonExchangeCode(Long userId, ExchangeCodeRequest exchangeCodeRequest);

    /**
     * 优惠券兑换，修改为实际用户
     * @param businessTag
     * @param recordbusinessKeyList
     * @param userId
     * @return
     */
    int updateRealUserByTagAndKey(String businessTag,List<String> recordbusinessKeyList, Long userId);

}
