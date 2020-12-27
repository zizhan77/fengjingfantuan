package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.ShowSeats;
import com.mem.vo.business.base.model.po.ShowSeatsQuery;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;

import java.util.List;

/**
 * <br>
 * <b>功能：</b>ShowSeatsService<br>
 */
public interface ShowSeatsService {

    Integer saveSeat(ShowSeats showSeats);

    /**
     * 添加接单中台表
     *
     * @param showSeatsQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */

    int insert(ShowSeats showSeatsQuery);

    /**
     * 更新接单中台表
     *
     * @param showSeats 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(ShowSeats showSeats);

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
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<ShowSeats> findByCondition(ShowSeatsQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<ShowSeats>  findPageByCondition(Page page, ShowSeatsQuery query);
}
