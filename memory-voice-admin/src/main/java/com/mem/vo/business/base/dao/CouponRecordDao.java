package com.mem.vo.business.base.dao;

import java.util.Date;
import java.util.List;

import com.mem.vo.business.base.model.po.CouponRecord;
import com.mem.vo.business.base.model.po.CouponRecordQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;
/**
*
* <br>
* <b>功能：</b>CouponRecordDao<br>
*/
public interface CouponRecordDao {

    /**
    * 添加优惠券记录表实体
    * @param  couponRecord 回复优惠券记录表实体
    * @return 返回添加的优惠券记录表的ID
    */
    int insert(CouponRecord couponRecord);

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
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<CouponRecord> findByCondition(@Param("condition") CouponRecordQuery query);

    /**
    * 根据条件查询优惠券记录表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    List<CouponRecord> findByCondition(@Param("page") Page page, @Param("condition") CouponRecordQuery query);

}
