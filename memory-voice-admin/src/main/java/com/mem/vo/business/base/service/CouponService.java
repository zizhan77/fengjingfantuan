package com.mem.vo.business.base.service;


import com.mem.vo.business.base.model.po.Coupon;
import com.mem.vo.business.base.model.po.CouponQuery;
import com.mem.vo.common.dto.Page;

import java.util.Date;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>CouponService<br>
*/
public interface CouponService {


    /**
    * 添加优惠券表
    * @param  couponQuery 回复优惠券表实体
    * @return 返回添加的优惠券表的ID
    */
    int insert(Coupon couponQuery);

    /**
    * 更新优惠券表
    * @param  coupon 更新优惠券表实体
    * @return 返回更新的优惠券表的ID
    */
    int updateById(Coupon coupon);

    /**
    * 删除优惠券表
    * @param  id 优惠券表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询优惠券表
    * @param  id 优惠券表ID
    * @return 返回一条优惠券表
    */
    Coupon findById(Long id);

    /**
    * 根据条件查询优惠券表
    * @param  query 查询优惠券表条件
    * @return 返回查询的集合
    */
    List<Coupon> findByCondition(CouponQuery query);

    /**
    * 根据条件查询优惠券表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    Page<Coupon> findPageByCondition(Page page, CouponQuery query);
}
