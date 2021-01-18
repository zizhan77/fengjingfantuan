package com.mem.vo.business.base.dao;


import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.PrizeDQuery;
import com.mem.vo.common.dto.Page;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select({"SELECT DISTINCT a.name,\n\n\t\t a.prize_id as prizeId,\n\t\t a.prize_type as prizeType,\n\t\t a.integral_qty as integralQty,\n\t\t a.prob as prob,\n\t\t a.eticket_code as eticketCode,a.prized_count as prizedCount,a.ticket_unit as ticketUnit,\n\t\t a.is_change as isChange,\n\t\t a.create_time as createTime,\n\t\t a.create_user as createUser,\n\t\t a.update_time as updateTime,\n\t\t a.update_user as updateUser,\n\t\t a.activity_id as activityId,\n\t\t a.prize_intro as prizeIntro,\n\t\t a.prize_name as prizeName,\n\t\t a.integral_prob as integralProb ,\n\t\t b.`total_num` as count,\n\t\t b.`level` as level \n FROM `prize_d` a LEFT JOIN `prize` b ON a.`prize_id` = b.`id` WHERE b.`activity_id` =#{query.activityId} AND  b.`prize_type` = #{query.prizeType} AND b.`level`=#{query.level}"})
    List<PrizeD> findByLevelAndActivityIdAndType(@Param("query") PrizeDQuery query);

    @Delete({"DELETE FROM `prize_d` WHERE `prize_id` = #{prizeId}"})
    int deleteByPrizeId(Integer prizeId);
}
