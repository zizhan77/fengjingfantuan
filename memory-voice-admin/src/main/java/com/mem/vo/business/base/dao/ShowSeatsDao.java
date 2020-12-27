package com.mem.vo.business.base.dao;

import com.mem.vo.business.base.model.po.ShowSeats;
import com.mem.vo.business.base.model.po.ShowSeatsQuery;
import com.mem.vo.common.dto.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>ShowSeatsDao<br>
 */
public interface ShowSeatsDao {

    /**
     * 添加接单中台表实体
     *
     * @param showSeats 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(ShowSeats showSeats);

    /**
     * 更新接单中台表
     *
     * @param showSeats 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(ShowSeats showSeats);
    int updateByIds(List<ShowSeats> seatsList);
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
    ShowSeats findById(Long id);


    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询条件
     * @return 返回查询的集合
     */
    List<ShowSeats> findByCondition(@Param("condition") ShowSeatsQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    List<ShowSeats> findByCondition(@Param("page") Page page, @Param("condition") ShowSeatsQuery query);

}
