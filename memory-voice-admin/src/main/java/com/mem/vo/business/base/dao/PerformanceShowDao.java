package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.PerformanceShow;
import com.mem.vo.business.base.model.po.PerformanceShowQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>PerformanceShowDao<br>
 */
public interface PerformanceShowDao {

    /**
     * 添加接单中台表实体
     *
     * @param performanceShow 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(PerformanceShow performanceShow);

    /**
     * 更新接单中台表
     *
     * @param performanceShow 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(PerformanceShow performanceShow);

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
    PerformanceShow findById(Long id);

    /**
     * 有SQL注入风险，后期需要优化
     * @param ids
     * @return
     */
    List<PerformanceShow> findByIds(@Param("ids") String ids);
    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<PerformanceShow> findByCondition(@Param("condition") PerformanceShowQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<PerformanceShow> findByCondition(@Param("page") Page page, @Param("condition") PerformanceShowQuery query);

}
