package com.mem.vo.business.base.service.impl;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.OrderDao;
import com.mem.vo.business.base.dao.ShowSeatsDao;
import com.mem.vo.business.base.dao.TicketStoreDao;
import com.mem.vo.business.base.dao.TicketStoreRecordDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.ShowSeatsService;
import com.mem.vo.business.base.service.TicketStoreService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.SeatStatus;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br>
 * <b>功能：</b>TicketStoreService<br>
 */
@Service("ticketStoreService")
public class TicketStoreServiceImpl implements TicketStoreService {

    private final static Logger log = LogManager.getLogger(TicketStoreServiceImpl.class);


    @Resource
    private TicketStoreDao ticketStoreDao;
    @Resource
    private TicketStoreRecordDao ticketStoreRecordDao;
    @Resource
    private ShowSeatsDao showSeatsDao;
    @Resource
    private OrderDao orderDao;

    public String rollBackStock(){
        (new Timer("StoreReleaseWorker")).schedule((TimerTask) new Object(), 1000L, 60000L);
//        new Timer("StoreReleaseWorker").schedule(new TimerTask() {
//            @Override
//            @Transactional(rollbackFor=Exception.class)
//            public void run() {
//                    TicketStoreRecordQuery tq = new TicketStoreRecordQuery();
//                    tq.setPayStatus(1);
//                    List<TicketStoreRecord> resList = ticketStoreRecordDao.findByCondition(tq);
//                    for (TicketStoreRecord t:resList){
//                        //释放失败订单预占库存
//                        OrderQuery o = new OrderQuery();
//                        o.setOrderNumber(t.getOrderId()+"");
//                        List<Order> orders = orderDao.findByCondition(o);
//                        if (orders == null) {
//                            throw new BizException(BizCode.BIZ_1050.getMessage());
//                        }
//                        Order curOrder = orders.get(0);
//                        TicketStoreQuery tsq = new TicketStoreQuery();
//                        tsq.setShowId(curOrder.getShowId());
//                        tsq.setTicketGearId(t.getTicketGearId());
//                        TicketStore ticketStore = ticketStoreDao.findByCondition(tsq).get(0);
//                        //释放座位
//                        if(t.getSeatIds()!=null){
//                            String [] seatArray = t.getSeatIds().split(",");
//                            for (String s:seatArray){
//                                ShowSeats showSeats = new ShowSeats();
//                                showSeats.setId(Long.parseLong(s));
//                                showSeats.setStatus(SeatStatus.ONSALE.getCode());
//                                //如果失败了，好像异常很严重0.0
//                                showSeatsDao.updateById(showSeats);
//                            }
//                        }
//                        //释放库存
//                        ticketStore.setTicketGearId(t.getTicketGearId());
//                        //ticketStore.setStoreNum(ticketStore.getStoreNum()+t.getOccupyNum());
//                        ticketStore.setSaleNum(ticketStore.getSaleNum()-t.getOccupyNum());
//                        ticketStoreDao.updateById(ticketStore);
//                        //释放库存流水记录
//                        t.setIsDelete(1);
//                        ticketStoreRecordDao.updateById(t);
//                        log.info("StoreReleaseWorker 释放库存成功");
//                    }
//            }
//        }, 1000,1000*60*1);
        return "创建worker成功";
    }

    @Override
    public int insert(TicketStore ticketStore) {
        return ticketStoreDao.insert(ticketStore);
    }

    @Override
    public int updateById(TicketStore ticketStore) {
        return ticketStoreDao.updateById(ticketStore);
    }



    @Override
    public int deleteById(Long id) {
        return ticketStoreDao.deleteById(id);
    }

    @Override
    public TicketStore findById(Long id) {
        return ticketStoreDao.findById(id);
    }

    @Override
    public List<TicketStore> findByCondition(TicketStoreQuery query) {
        return ticketStoreDao.findByCondition(query);
    }

    @Override
    public Page<TicketStore> findPageByCondition(Page page, TicketStoreQuery query) {
        List<TicketStore> list = ticketStoreDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
