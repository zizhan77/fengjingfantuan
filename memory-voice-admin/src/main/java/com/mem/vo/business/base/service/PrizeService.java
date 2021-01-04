package com.mem.vo.business.base.service;

import java.util.List;

import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.PrizeQuery;
import com.mem.vo.common.dto.Page;

/**
 * <br>
 * <b>功能：</b>PrizeService<br>
 */
public interface PrizeService {


    /**
     * 添加接单中台表
     *
     * @param prizeQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Prize prizeQuery);

    /**
     * 更新接单中台表
     *
     * @param prize 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Prize prize);

    /**
     * 删除接单中台表
     *
     * @param id 接单中台表ID
     */
    int deleteById(Long id);

    /**
     * 根据ID查询接单中台表
     *
     * @param id 接单中台表ID
     * @return 返回一条接单中台表
     */
    Prize findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<Prize> findByCondition(PrizeQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<Prize> findPageByCondition(Page page, PrizeQuery query);

    /**
     * 查询中奖信息
     * @param token 用户token
     * @return  返回中奖信息
     */
    PrizeD slotMachine(String token, Long activityId);

    int rollBackStoreById(Integer paramInteger);

    int addchangecodeall(Long paramLong);
}
