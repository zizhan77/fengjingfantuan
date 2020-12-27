package com.mem.vo.business.base.service;

import java.util.List;

import com.mem.vo.business.base.model.po.Order;
import com.mem.vo.business.base.model.po.OrderQuery;
import com.mem.vo.business.base.model.vo.OderVO;
import com.mem.vo.common.dto.Page;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * <br>
 * <b>功能：</b>OrderService<br>
 */
public interface OrderService {


    /**
     * 添加接单中台表
     *
     * @param orderQuery 回复接单中台表实体
     * @return 返回添加的接单中台表的ID
     */
    int insert(Order orderQuery);

    /**
     * 更新接单中台表
     *
     * @param order 更新接单中台表实体
     * @return 返回更新的接单中台表的ID
     */
    int updateById(Order order);

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
    Order findById(Long id);

    /**
     * 根据条件查询接单中台表
     *
     * @param query 查询接单中台表条件
     * @return 返回查询的集合
     */
    List<Order> findByCondition(OrderQuery query);

    /**
     * 根据条件查询接单中台表
     *
     * @param page 分页信息
     * @param query 查询条件
     */
    Page<Order> findPageByCondition(Page page, OrderQuery query);


    boolean createOrderByExchangeCode(String token, String exchangeCode);

    List<OderVO> findMyOrderListByUserId(OrderQuery query);
}
