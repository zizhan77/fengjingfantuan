package com.mem.vo.business.base.service;

import java.util.List;

import com.mem.vo.business.base.model.po.TicketGear;
import com.mem.vo.business.base.model.po.TicketGearQuery;
import com.mem.vo.common.dto.Page;

/**
 * <br>
 * <b>功能：</b>TicketGearService<br>
 */
public interface TicketGearService {


    /**
     * 添加接单中台表
     *
     * @param ticketGearQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(TicketGear ticketGearQuery);

    /**
     * 更新接单中台表
     *
     * @param ticketGear 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(TicketGear ticketGear);

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
    TicketGear findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<TicketGear> findByCondition(TicketGearQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<TicketGear> findPageByCondition(Page page, TicketGearQuery query);
}
