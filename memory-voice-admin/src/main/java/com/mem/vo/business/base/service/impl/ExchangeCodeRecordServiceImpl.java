package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ExchangeCodeRecordDao;
import com.mem.vo.business.base.model.po.ExchangeCodeRecord;
import com.mem.vo.business.base.model.po.ExchangeCodeRecordQuery;
import com.mem.vo.business.base.service.ExchangeCodeRecordService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>ExchangeCodeRecordService<br>
 */
@Service("exchangeCodeRecordService")
public class ExchangeCodeRecordServiceImpl implements ExchangeCodeRecordService {

    private final static Logger log = LogManager.getLogger(ExchangeCodeRecordServiceImpl.class);


    @Resource
    private ExchangeCodeRecordDao exchangeCodeRecordDao;

    @Override
    public int insert(ExchangeCodeRecord exchangeCodeRecord) {
        return exchangeCodeRecordDao.insert(exchangeCodeRecord);
    }

    @Override
    public int updateById(ExchangeCodeRecord exchangeCodeRecord) {
        return exchangeCodeRecordDao.updateById(exchangeCodeRecord);
    }

    @Override
    public int deleteById(Long id) {
        return exchangeCodeRecordDao.deleteById(id);
    }

    @Override
    public ExchangeCodeRecord findById(Long id) {
        return exchangeCodeRecordDao.findById(id);
    }

    @Override
    public List<ExchangeCodeRecord> findByCondition(ExchangeCodeRecordQuery query) {
        return exchangeCodeRecordDao.findByCondition(query);
    }

    @Override
    public Page<ExchangeCodeRecord> findPageByCondition(Page page, ExchangeCodeRecordQuery query) {
        List<ExchangeCodeRecord> list = exchangeCodeRecordDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }


    @Override
    public int updateByExchangeCode(String exchangeCode, String oldStatus,String status) {
        return  exchangeCodeRecordDao.updateByExchangeCode(exchangeCode,oldStatus,status);
    }
}
