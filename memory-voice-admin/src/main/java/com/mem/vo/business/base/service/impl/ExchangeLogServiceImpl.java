package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.ExchangeLogDao;
import com.mem.vo.business.base.model.po.ExchangeLog;
import com.mem.vo.business.base.model.po.ExchangeLogQuery;
import com.mem.vo.business.base.service.ExchangeLogService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
*
* <br>
* <b>功能：</b>ExchangeLogService<br>
*/
@Service("exchangeLogService")
public class  ExchangeLogServiceImpl implements ExchangeLogService {
    private final static Logger log= LogManager.getLogger(ExchangeLogServiceImpl.class);


    @Resource
    private ExchangeLogDao exchangeLogDao;

    @Override
    public int insert(ExchangeLog exchangeLog){
        return exchangeLogDao.insert(exchangeLog);
    }

    @Override
    public int updateById(ExchangeLog exchangeLog){
        return exchangeLogDao.updateById(exchangeLog);
    }

    @Override
    public int deleteById(Long id){
        return exchangeLogDao.deleteById(id);
    }

    @Override
    public ExchangeLog findById(Long id){
        return exchangeLogDao.findById(id);
    }

    @Override
    public List<ExchangeLog> findByCondition(ExchangeLogQuery query){
        return exchangeLogDao.findByCondition(query);
    }

    @Override
    public Page<ExchangeLog> findPageByCondition(Page page, ExchangeLogQuery query){
        List<ExchangeLog> list = exchangeLogDao.findByCondition(page,query);
        page.setData(list);
        return page;
    }
}
