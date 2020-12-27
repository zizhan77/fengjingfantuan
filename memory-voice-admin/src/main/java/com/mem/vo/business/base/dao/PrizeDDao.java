package com.mem.vo.business.base.dao;


import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.PrizeDQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>PrizeDDao<br>
*/
public interface PrizeDDao {

    /**
    * 添加奖品明细表实体
    * @param  prizeD 回复奖品明细表实体
    * @return 返回添加的奖品明细表的ID
    */
    int insert(PrizeD prizeD);

    int insertBatch(@Param("list") List<PrizeD> list);
    /**
    * 更新奖品明细表
    * @param  prizeD 更新奖品明细表实体
    * @return 返回更新的奖品明细表的ID
    */
    int updateById(PrizeD prizeD);

    /**
    * 删除奖品明细表
    * @param  ids 奖品明细表ID
    */
    int deleteById(@Param("ids")List<Long> ids);

    /**
    * 根据ID查询奖品明细表
    * @param  id 奖品明细表ID
    * @return 返回一条奖品明细表
    */
    PrizeD findById(Long id);


    /**
    * 根据条件查询奖品明细表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<PrizeD> findByCondition(@Param("condition") PrizeDQuery query);

    /**
    * 根据条件查询奖品明细表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    List<PrizeD> findByCondition(@Param("page") Page page, @Param("condition") PrizeDQuery query);

    List<Integer> selectIdByEt(@Param("condition") PrizeDQuery query);
}
