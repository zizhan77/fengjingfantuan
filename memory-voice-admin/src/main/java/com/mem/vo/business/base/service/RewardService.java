package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.IntegralRoleClass;
import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.model.vo.RewardQueryDto;
import com.mem.vo.business.base.model.vo.RewardVO;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import java.util.List;
/**
*
* <br>
* <b>功能：</b>RewardService<br>
*/
public interface RewardService {


    /**
    * 添加奖品信息明细表
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
    * @param  query 查询奖品信息明细表条件
    * @return 返回查询的集合
    */
    List<Reward> findByCondition(RewardQuery query);

    List<Reward> queryMaterialAndTicketList(RewardQuery query);
    /**
    * 根据条件查询奖品信息明细表
    * @param  page 分页信息
    * @param  query 查询条件
    */
    void findPageByCondition(Page page, RewardQuery query);

    List<Integer> selectIdByEt(RewardQuery query);

    List<RewardVO> findBy(String token);

    List<Reward> findByConditionAndDate(RewardQuery reward);

    int updateByPrizedId(Integer prizedId, Integer code);

    Integer getUserDefaultIntegral();

    List<RewardVO> findByActivityId(Integer activityId);

    List<Reward> findByConditionAndCurrentDay(RewardQuery reward);

    PageBean<RewardVO> queryListBy(RewardQueryDto dto);

    void downLoadExcel(RewardQueryDto dto);

    List<IntegralRoleClass> integralRoleList(Integer id);

    Integer updateByIdToIntegralRole(IntegralRoleClass roleClass);

    PageBean<RewardVO> queryListByUser(Long userId, Integer flag, Integer pageNo, Integer pageSize) throws Exception;

    int getOldUserIntegral();

    int getNewUserIntegral();

    IntegralRoleClass getSignlaxin();

    Integer updateRewardFlag(String token, Reward reward);
}
