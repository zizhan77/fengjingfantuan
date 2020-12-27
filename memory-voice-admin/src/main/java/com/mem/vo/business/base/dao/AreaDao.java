package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.Area;
import com.mem.vo.business.base.model.po.AreaQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>AreaDao<br>
 */
public interface AreaDao {

    /**
     * 添加接单中台表实体
     *
     * @param area 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Area area);

    /**
     * 更新接单中台表
     *
     * @param area 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Area area);

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
    Area findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<Area> findByCondition(@Param("condition") AreaQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<Area> findByCondition(@Param("page") Page page, @Param("condition") AreaQuery query);

}
