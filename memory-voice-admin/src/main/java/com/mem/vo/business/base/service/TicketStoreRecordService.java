package com.mem.vo.business.base.service;

import java.util.List;

import com.mem.vo.business.base.model.po.TicketStoreRecord;
import com.mem.vo.business.base.model.po.TicketStoreRecordQuery;
import com.mem.vo.common.dto.Page;
import org.springframework.scheduling.annotation.Async;

/**
 * <br>
 * <b>功能：</b>TicketStoreRecordService<br>
 */
public interface TicketStoreRecordService {

    @Async
    void createTimerForTicketRecord(String orderNumber);
    /**
     * 添加接单中台表
     *
     * @param ticketStoreRecordQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(TicketStoreRecord ticketStoreRecordQuery);

    /**
     * 更新接单中台表
     *
     * @param ticketStoreRecord 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(TicketStoreRecord ticketStoreRecord);

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
    TicketStoreRecord findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<TicketStoreRecord> findByCondition(TicketStoreRecordQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<TicketStoreRecord> findPageByCondition(Page page, TicketStoreRecordQuery query);
}
