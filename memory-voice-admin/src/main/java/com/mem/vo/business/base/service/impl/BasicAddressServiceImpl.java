package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.BasicAddressDao;
import com.mem.vo.business.base.model.po.BasicAddress;
import com.mem.vo.business.base.model.po.BasicAddressQuery;
import com.mem.vo.business.base.service.BasicAddressService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>BasicAddressService<br>
 */
@Service("basicAddressService")
public class BasicAddressServiceImpl implements BasicAddressService {

    private final static Logger log = LogManager.getLogger(BasicAddressServiceImpl.class);


    @Resource
    private BasicAddressDao basicAddressDao;

    @Override
    public int insert(BasicAddress basicAddress) {
        return basicAddressDao.insert(basicAddress);
    }

    @Override
    public int updateById(BasicAddress basicAddress) {
        return basicAddressDao.updateById(basicAddress);
    }

    @Override
    public int deleteById(Long id) {
        return basicAddressDao.deleteById(id);
    }

    @Override
    public BasicAddress findById(Long id) {
        return basicAddressDao.findById(id);
    }

    @Override
    public List<BasicAddress> findByCondition(BasicAddressQuery query) {
        return basicAddressDao.findByCondition(query);
    }

    @Override
    public Page<BasicAddress> findPageByCondition(Page page, BasicAddressQuery query) {
        List<BasicAddress> list = basicAddressDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Override
    public String findNameByCode(String code) {

        return basicAddressDao.findNameByCode(code);
    }
}
