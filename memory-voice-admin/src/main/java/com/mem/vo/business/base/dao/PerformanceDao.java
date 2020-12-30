package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.PerformanceQuery;
import com.mem.vo.business.biz.model.vo.performance.ShowDetail;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>PerformanceDao<br>
 */
public interface PerformanceDao {

    /**
     * 添加接单中台表实体
     *
     * @param performance 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Performance performance);

    /**
     * 更新接单中台表
     *
     * @param performance 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Performance performance);

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
    Performance findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<Performance> findByCondition(@Param("condition") PerformanceQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<Performance> findByCondition(@Param("page") Page page, @Param("condition") PerformanceQuery query);


    List<Performance> findByVo(@Param("page") Page page, @Param("condition") PerformanceQuery performanceQuery);

    List<Performance> selectByUserId(Integer userId);

    List<ShowDetail> getListByCount();
}
