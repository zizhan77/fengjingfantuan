package com.mem.vo.business.base.service.impl;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.OrderDao;
import com.mem.vo.business.base.dao.TicketStoreRecordDao;
import com.mem.vo.business.base.model.po.Order;
import com.mem.vo.business.base.model.po.OrderQuery;
import com.mem.vo.business.base.model.po.TicketStoreRecord;
import com.mem.vo.business.base.model.po.TicketStoreRecordQuery;
import com.mem.vo.business.base.service.TicketStoreRecordService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br>
 * <b>功能：</b>TicketStoreRecordService<br>
 */
@Service("ticketStoreRecordService")
public class TicketStoreRecordServiceImpl implements TicketStoreRecordService {

    private final static Logger log = LogManager.getLogger(TicketStoreRecordServiceImpl.class);


    @Resource
    private TicketStoreRecordDao ticketStoreRecordDao;
    @Resource
    private OrderDao orderDao;

    @Override
    @Async
    public void  createTimerForTicketRecord(String orderNumber){
        new Timer("" + orderNumber).schedule(new TimerTask() {
            @Override
            @Transactional(rollbackFor=Exception.class)
            public void run() {
                try{
                    OrderQuery query = new OrderQuery();
                    query.setOrderNumber(Thread.currentThread().getName());
                    List<Order> res = orderDao.findByCondition(query);
                    if(res.size() > 0 && res.get(0).getStatus() > 0){
                        log.info("支付完成，预占状态解除");
                    }else{
                        TicketStoreRecordQuery tq = new TicketStoreRecordQuery();
                        //orderId实际为orderNumber
                        tq.setOrderId(Long.parseLong(Thread.currentThread().getName()));
                        List<TicketStoreRecord> resList = ticketStoreRecordDao.findByCondition(tq);
                        for (TicketStoreRecord t:resList){
                            //更新为支付失败状态
                            t.setPayStatus(1);
                            ticketStoreRecordDao.updateById(t);
                        }
                        OrderQuery oq = new OrderQuery();
                        oq.setOrderNumber(Thread.currentThread().getName());
                        List<Order> ordersList = orderDao.findByCondition(oq);
                        for (Order o:ordersList){
                            //更新为支付失败状态
                            o.setStatus(-1);
                            orderDao.updateById(o);
                        }
                    }
                }catch (Exception e){
                    createTimerForTicketRecord(Thread.currentThread().getName());
                }
            }
        }, 1000*60*15);
    }

    @Override
    public int insert(TicketStoreRecord ticketStoreRecord) {
        return ticketStoreRecordDao.insert(ticketStoreRecord);
    }

    @Override
    public int updateById(TicketStoreRecord ticketStoreRecord) {
        return ticketStoreRecordDao.updateById(ticketStoreRecord);
    }

    @Override
    public int deleteById(Long id) {
        return ticketStoreRecordDao.deleteById(id);
    }

    @Override
    public TicketStoreRecord findById(Long id) {
        return ticketStoreRecordDao.findById(id);
    }

    @Override
    public List<TicketStoreRecord> findByCondition(TicketStoreRecordQuery query) {
        return ticketStoreRecordDao.findByCondition(query);
    }

    @Override
    public Page<TicketStoreRecord> findPageByCondition(Page page, TicketStoreRecordQuery query) {
        List<TicketStoreRecord> list = ticketStoreRecordDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
