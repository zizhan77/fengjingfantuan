package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.PerformanceShowDao;
import com.mem.vo.business.base.model.po.PerformanceShow;
import com.mem.vo.business.base.model.po.PerformanceShowQuery;
import com.mem.vo.business.base.service.PerformanceShowService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>PerformanceShowService<br>
 */
@Service("performanceShowService")
public class PerformanceShowServiceImpl implements PerformanceShowService {

    private final static Logger log = LogManager.getLogger(PerformanceShowServiceImpl.class);


    @Resource
    private PerformanceShowDao performanceShowDao;

    @Override
    public int insert(PerformanceShow performanceShow) {
        return performanceShowDao.insert(performanceShow);
    }

    @Override
    public int updateById(PerformanceShow performanceShow) {
        return performanceShowDao.updateById(performanceShow);
    }

    @Override
    public int deleteById(Long id) {
        return performanceShowDao.deleteById(id);
    }

    @Override
    public PerformanceShow findById(Long id) {
        return performanceShowDao.findById(id);
    }

    @Override
    public List<PerformanceShow> findByCondition(PerformanceShowQuery query) {
        return performanceShowDao.findByCondition(query);
    }

    @Override
    public Page<PerformanceShow> findPageByCondition(Page page, PerformanceShowQuery query) {
        List<PerformanceShow> list = performanceShowDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Override
    public List<PerformanceShow> findByIds(String ids) {
        return performanceShowDao.findByIds(ids);
    }
}
