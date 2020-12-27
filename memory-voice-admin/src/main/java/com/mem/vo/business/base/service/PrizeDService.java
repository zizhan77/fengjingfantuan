package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.PrizeDQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>PrizeDService<br>
*/
public interface PrizeDService {


    /**
    * 添加奖品明细表
    * @param  prizeDQuery 回复奖品明细表实体
    * @return 返回添加的奖品明细表的ID
    */
    int insert(PrizeD prizeDQuery);

    int insertBatch(List<PrizeD> prizeDQuery);
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
    int deleteById(List<Long> ids);

    /**
    * 根据ID查询奖品明细表
    * @param  id 奖品明细表ID
    * @return 返回一条奖品明细表
    */
    PrizeD findById(Long id);

    /**
    * 根据条件查询奖品明细表
    * @param  query 查询奖品明细表条件
    * @return 返回查询的集合
    */
    List<PrizeD> findByCondition(PrizeDQuery query);

    /**
    * 根据条件查询奖品明细表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, PrizeDQuery query);

    List<Integer> selectIdByEt(PrizeDQuery query);
}
