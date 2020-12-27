package com.mem.vo.business.base.dao;

import java.util.List;

import com.mem.vo.business.base.model.po.Seats;
import com.mem.vo.business.base.model.po.SeatsQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <br>
 * <b>功能：</b>SeatsDao<br>
 */
public interface SeatsDao {

    /**
     * 添加接单中台表实体
     *
     * @param seats 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Seats seats);

    /**
     * 更新接单中台表
     *
     * @param seats 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Seats seats);

    int updateByIds(List<Seats> seatsList);

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
    Seats findById(Long id);

    List<Seats> findByIds(@Param("ids") String ids);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<Seats> findByCondition(@Param("condition") SeatsQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<Seats> findByCondition(@Param("page") Page page, @Param("condition") SeatsQuery query);

}
