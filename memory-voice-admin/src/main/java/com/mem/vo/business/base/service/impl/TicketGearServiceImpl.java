package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.TicketGearDao;
import com.mem.vo.business.base.model.po.TicketGear;
import com.mem.vo.business.base.model.po.TicketGearQuery;
import com.mem.vo.business.base.service.TicketGearService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>TicketGearService<br>
 */
@Service("ticketGearService")
public class TicketGearServiceImpl implements TicketGearService {

    private final static Logger log = LogManager.getLogger(TicketGearServiceImpl.class);


    @Resource
    private TicketGearDao ticketGearDao;

    @Override
    public int insert(TicketGear ticketGear) {
        return ticketGearDao.insert(ticketGear);
    }

    @Override
    public int updateById(TicketGear ticketGear) {
        return ticketGearDao.updateById(ticketGear);
    }

    @Override
    public int deleteById(Long id) {
        return ticketGearDao.deleteById(id);
    }

    @Override
    public TicketGear findById(Long id) {
        return ticketGearDao.findById(id);
    }

    @Override
    public List<TicketGear> findByCondition(TicketGearQuery query) {
        return ticketGearDao.findByCondition(query);
    }

    @Override
    public Page<TicketGear> findPageByCondition(Page page, TicketGearQuery query) {
        List<TicketGear> list = ticketGearDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
