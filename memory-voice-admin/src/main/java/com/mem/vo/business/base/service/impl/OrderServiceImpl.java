package com.mem.vo.business.base.service.impl;


import java.util.*;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.ExchangeCodeMainDao;
import com.mem.vo.business.base.dao.ExchangeCodeRecordDao;
import com.mem.vo.business.base.dao.OrderDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.model.vo.OderVO;
import com.mem.vo.business.base.model.vo.PerformanceOrderVo;
import com.mem.vo.business.base.service.*;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.ExRecordStatusEnum;
import com.mem.vo.common.constant.ExchangeCodeBizType;
import com.mem.vo.common.constant.OrderStatusEnum;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br>
 * <b>功能：</b>OrderService<br>
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LogManager.getLogger(OrderServiceImpl.class);


    @Resource
    private OrderDao orderDao;
    @Resource
    private SeatsService seatsService;
    @Resource
    private BasicPlaceService basicPlaceService;
    @Resource
    private PerformanceService performanceService;
    @Resource
    private ExchangeCodeRecordDao exchangeCodeRecordDao;
    @Resource
    private ExchangeCodeMainDao exchangeCodeMainDao;
    @Resource
    private PerformanceShowService performanceShowService;

    @Override
    public int insert(Order order) {
        return orderDao.insert(order);
    }

    @Override
    public int updateById(Order order) {
        return orderDao.updateById(order);
    }

    @Override
    public int deleteById(Long id) {
        return orderDao.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByCondition(OrderQuery query) {
        return orderDao.findByCondition(query);
    }

    @Override
    public Page<Order> findPageByCondition(Page page, OrderQuery query) {
        List<Order> list = orderDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
    //需要关联主表修改的方法

    @Transactional
    @CommonExHandler(key = "使用兑换码创建订单")
    @Override
    public boolean createOrderByExchangeCode(String token, String exchangeCode) {
        ExchangeCodeRecordQuery ecq = new ExchangeCodeRecordQuery();
        ecq.setExchangeCode(exchangeCode);
        ecq.setBusinessTag(ExchangeCodeBizType.FOR_TICKET.getCode());
        List<ExchangeCodeRecord> records = exchangeCodeRecordDao.findByCondition(ecq);
        if (records.size() <= 0 || records.size() > 1) {
            throw new BizException(BizCode.BIZ_1052.getMessage());
        }
        ExchangeCodeRecord recordPo = records.get(0);
        Order orderPo = new Order();
        orderPo.setCreateTime(new Date());
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR));
        sb.append(Calendar.getInstance().getTimeInMillis());
        sb.append((int) Math.random() * 10);
        orderPo.setOrderNumber(sb.toString());
        orderPo.setStatus(OrderStatusEnum.FINISHED.getCode());
        if (recordPo.getBusinessTag().equals(ExchangeCodeBizType.FOR_NOSEAT_TICKET)) {
            orderPo.setTicketGearId(Long.parseLong(recordPo.getBusinessKey()));
        } else {
            orderPo.setSeatIds(recordPo.getBusinessKey());
        }
        recordPo.setBusinessKey(orderPo.getOrderNumber());
        recordPo.setStatus(ExRecordStatusEnum.USED.getCode());
        exchangeCodeRecordDao.updateById(recordPo);
        ExchangeCodeMain main = exchangeCodeMainDao.findById(recordPo.getMainId());
        main.setChangedNum(main.getChangedNum() + 1);
        exchangeCodeMainDao.updateById(main);
        orderDao.insert(orderPo);
        return true;
    }

    @Override
    public List<OderVO> findMyOrderListByUserId(OrderQuery query) {
        List<Order> list = orderDao.findByCondition(query);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<OderVO> voList = new ArrayList<>(list.size());
        //移除
        list.removeIf(item -> item.getStatus().equals(OrderStatusEnum.CANCELED.getCode()));
        list.stream().sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        if (CollectionUtils.isEmpty(list)) {
            return voList;
        }
        for (Order order : list) {
            OderVO vo = new OderVO();
            BeanUtils.copyProperties(order, vo);
            voList.add(vo);
            if (order.getPerformanceId() == null) {
                continue;
            }
            Performance performanceOriginal = performanceService.findById(order.getPerformanceId());
            PerformanceOrderVo performance = new PerformanceOrderVo();
            BeanUtils.copyProperties(performanceOriginal, performance);
            vo.setPerformance(performance);
            Long showId = order.getShowId();
            PerformanceShow performanceShow;
            if (showId != null && (performanceShow = performanceShowService.findById(showId)) != null) {
                Date firstShowTime = performanceShow.getStartTime();
                BizAssert.notNull(firstShowTime, "获取第一场演出时间为空");
                performance.setShowTime(firstShowTime);
                //状态转换
                Integer status = 0;
                status = performanceService.getStatus(performance.getStartSaleDate(), firstShowTime, status);
                performance.setStatus(status);
            }
            if (performance.getPlaceId() == null) {
                continue;
            }
            BasicPlace basicPlace = basicPlaceService.findById(performance.getPlaceId());
            vo.setPlace(basicPlace);
        }
        return voList;
    }
}
