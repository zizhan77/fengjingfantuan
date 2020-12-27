package com.mem.vo.business.base.service;


import com.mem.vo.business.base.model.po.CouponRecord;
import com.mem.vo.business.base.model.po.CouponRecordQuery;
import com.mem.vo.common.dto.Page;

import java.util.Date;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>CouponRecordService<br>
*/
public interface CouponRecordService {


    /**
    * 添加优惠券记录表
    * @param  couponRecordQuery 回复优惠券记录表实体
    * @return 返回添加的优惠券记录表的ID
    */
    int insert(CouponRecord couponRecordQuery);

    /**
    * 更新优惠券记录表
    * @param  couponRecord 更新优惠券记录表实体
    * @return 返回更新的优惠券记录表的ID
    */
    int updateById(CouponRecord couponRecord);

    /**
    * 删除优惠券记录表
    * @param  id 优惠券记录表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询优惠券记录表
    * @param  id 优惠券记录表ID
    * @return 返回一条优惠券记录表
    */
    CouponRecord findById(Long id);

    /**
    * 根据条件查询优惠券记录表
    * @param  query 查询优惠券记录表条件
    * @return 返回查询的集合
    */
    List<CouponRecord> findByCondition(CouponRecordQuery query);

    /**
    * 根据条件查询优惠券记录表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    List<CouponRecord> findPageByCondition(Page page, CouponRecordQuery query);
}
