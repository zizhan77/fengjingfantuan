package com.mem.vo.business.base.service;

import java.util.List;

import com.mem.vo.business.base.model.po.UserScore;
import com.mem.vo.business.base.model.po.UserScoreQuery;
import com.mem.vo.common.dto.Page;

/**
 * <br>
 * <b>功能：</b>UserScoreService<br>
 */
public interface UserScoreService {


    /**
     * 添加接单中台表
     *
     * @param userScoreQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(UserScore userScoreQuery);

    /**
     * 更新接单中台表
     *
     * @param userScore 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(UserScore userScore);

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
    UserScore findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<UserScore> findByCondition(UserScoreQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<UserScore> findPageByCondition(Page page, UserScoreQuery query);
}
