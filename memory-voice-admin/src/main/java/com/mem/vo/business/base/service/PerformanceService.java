package com.mem.vo.business.base.service;

import java.util.Date;
import java.util.List;

import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.PerformanceQuery;
import com.mem.vo.business.biz.model.vo.performance.ShowDetail;
import com.mem.vo.common.dto.Page;

/**
 * <br>
 * <b>功能：</b>PerformanceService<br>
 */
public interface PerformanceService {


    /**
     * 添加接单中台表
     *
     * @param performanceQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Performance performanceQuery);

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
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<Performance> findByCondition(PerformanceQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<Performance> findPageByCondition(Page page, PerformanceQuery query);

    ShowDetail getDetail(Long id);

    ShowDetail getShowDetail(Performance performance);


    List<ShowDetail> getList();

    /**
     * 获取演出状态
     * @param startSaleDate 开始售卖时间
     * @param firstShowTime 首场演出时间== 销售结束时间
     * @param status
     * @return
     */
    Integer getStatus(Date startSaleDate, Date firstShowTime, Integer status);

    Page<Performance> findByPageVo(Page page, PerformanceQuery performanceQuery);

    List<Performance> selectByUserId(Integer userId);
}
