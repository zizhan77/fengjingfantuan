package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.PrizeDQuery;
import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
*
* <br>
* <b>功能：</b>RewardDao<br>
*/
public interface RewardDao {

    /**
    * 添加奖品信息明细表实体
    * @param  reward 回复奖品信息明细表实体
    * @return 返回添加的奖品信息明细表的ID
    */
    int insert(Reward reward);

    /**
    * 更新奖品信息明细表
    * @param  reward 更新奖品信息明细表实体
    * @return 返回更新的奖品信息明细表的ID
    */
    int updateById(Reward reward);

    /**
    * 删除奖品信息明细表
    * @param  id 奖品信息明细表ID
    */
    int deleteById(Long id);

    /**
    * 根据ID查询奖品信息明细表
    * @param  id 奖品信息明细表ID
    * @return 返回一条奖品信息明细表
    */
    Reward findById(Long id);


    /**
    * 根据条件查询奖品信息明细表
    * @param  query 查询条件
    * @return 返回查询的集合
    */
    List<Reward> findByCondition(@Param("condition") RewardQuery query);

    /**
    * 根据条件查询奖品信息明细表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    List<Reward> findByCondition(@Param("page") Page page, @Param("condition") RewardQuery query);

    List<Integer> selectIdByEt(@Param("condition") RewardQuery query);
}
