package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.IntegralQuery;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;

/**
*
* <br>
* <b>功能：</b>IntegralService<br>
*/
public interface IntegralService {


    /**
    * 添加积分表
    * @param  integralQuery 回复积分表实体
    * @return 返回添加的积分表的ID
    */
    int insert(Integral integralQuery);

    /**
    * 更新积分表
    * @param  integral 更新积分表实体
    * @return 返回更新的积分表的ID
    */
    int updateById(Integral integral);

    /**
    * 删除积分表
    * @param  id 积分表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询积分表
    * @param  id 积分表ID
    * @return 返回一条积分表
    */
    Integral findById(Long id);

    /**
    * 根据条件查询积分表
    * @param  query 查询积分表条件
    * @return 返回查询的集合
    */
    List<Integral> findByCondition(IntegralQuery query);

    /**
    * 根据条件查询积分表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, IntegralQuery query);

    List<Integral> integralRecordByUser(Long paramLong, PageBean<Integral> paramPageBean);
}
