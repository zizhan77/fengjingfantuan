package com.mem.vo.business.base.service.impl;


import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.BasicFreightDao;
import com.mem.vo.business.base.model.po.BasicFreight;
import com.mem.vo.business.base.model.po.BasicFreightQuery;
import com.mem.vo.business.base.service.BasicFreightService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>BasicFreightService<br>
 */
@Service("basicFreightService")
public class BasicFreightServiceImpl implements BasicFreightService {

    private final static Logger log = LogManager.getLogger(BasicFreightServiceImpl.class);


    @Resource
    private BasicFreightDao basicFreightDao;

    @Override
    public int insert(BasicFreight basicFreight) {
        return basicFreightDao.insert(basicFreight);
    }

    @Override
    public int updateById(BasicFreight basicFreight) {
        return basicFreightDao.updateById(basicFreight);
    }

    @Override
    public int deleteById(Long id) {
        return basicFreightDao.deleteById(id);
    }

    @Override
    public BasicFreight findById(Long id) {
        return basicFreightDao.findById(id);
    }

    @Override
    public List<BasicFreight> findByCondition(BasicFreightQuery query) {
        return basicFreightDao.findByCondition(query);
    }

    @Override
    public Page<BasicFreight> findPageByCondition(Page page, BasicFreightQuery query) {
        List<BasicFreight> list = basicFreightDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Override
    public BigDecimal getFreightByOneAddress(String oneAddressCode) {

        BasicFreightQuery query = new BasicFreightQuery();
        query.setOneAddress(oneAddressCode);
        query.setIsDelete(0);
        List<BasicFreight> freightList = this.findByCondition(query);
        BizAssert.notEmpty(freightList,"省份不存在");
        BasicFreight basicFreight = freightList.get(0);
        BizAssert.notNull(basicFreight.getFreight(),"费用为空");
        if(basicFreight.getFreight().compareTo(new BigDecimal("-1"))==0){
            query.setOneAddress("-999");
            List<BasicFreight> defaultList = this.findByCondition(query);
            BizAssert.notEmpty(defaultList,"获取默认运费为空");
           return defaultList.get(0).getFreight();
        }else{
            return basicFreight.getFreight();
        }
    }
}
