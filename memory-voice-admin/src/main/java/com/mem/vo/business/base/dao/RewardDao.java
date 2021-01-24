package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.IntegralRoleClass;
import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.model.vo.RewardQueryDto;
import com.mem.vo.business.base.model.vo.RewardVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
*
* <br>
* <b>功能：</b>RewardDao<br>
*/
public interface RewardDao {

    List<Reward> getUserForTickrt(@Param("userid") Long userid, @Param("first") String first, @Param("last") String last, @Param("code") Integer code);

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

    List<RewardVO> findBy(@Param("query") RewardQuery paramRewardQuery, @Param("page") Page paramPage);

    List<Reward> findByConditionAndDate(@Param("condition") RewardQuery paramRewardQuery);

    List<RewardVO> findByUserId(@Param("id") Long paramLong);

    @Update({"update reward set status = #{status} where prize_id = #{id}"})
    int updateByPrizedId(@Param("id") Integer id, @Param("status") Integer status);

    List<RewardVO> findByActivityId(@Param("activityId") Integer activityId);

    List<Reward> findByConditionAndCurrentDay(@Param("condition") RewardQuery rewardQuery);

    int queryListByCount(@Param("dto") RewardQueryDto rewardQueryDto);

    List<RewardVO> queryListBy(@Param("paging") PageBean<RewardVO> paging, @Param("dto") RewardQueryDto rewardQueryDto);

    List<IntegralRoleClass> integralRoleList(@Param("id") Integer id);

    Integer updateByIdToIntegralRole(IntegralRoleClass integralRoleClass);

    Integer getUserIntegralFlagCount();

    int queryListByUserCount(@Param("id") Long id, @Param("type") Integer type);

    List<RewardVO> queryListByUser(@Param("paging") PageBean<RewardVO> paging, @Param("id") Long id, @Param("type") Integer type);

    Integer getUserDefaultIntegral();

    int getNewUserIntegral();

    Integer updateRewardFlag(Integer paramInteger);

    List<RewardVO> queryYouhuiquan(@Param("paging") PageBean<RewardVO> paging, @Param("id") Long id, @Param("type") Integer type);
}
