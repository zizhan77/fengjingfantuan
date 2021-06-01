package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.VisitDao;
import com.mem.vo.business.base.model.po.Visit;
import com.mem.vo.business.base.model.po.VisitQuery;
import com.mem.vo.business.base.model.vo.VisitVO;
import com.mem.vo.business.base.service.VisitService;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.PageBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private final static Logger log = LogManager.getLogger(VisitServiceImpl.class);


    @Resource
    private VisitDao visitDao;

    @Override
    public int insert(Visit visit) {
        Date date = new Date();
        visit.setCreateTime(date);
        visit.setUpdateTime(date);
        return visitDao.insert(visit);
    }

    @Override
    public int updateById(Visit visit) {
        return visitDao.updateById(visit);
    }

    @Override
    public int deleteById(Long id) {
        return visitDao.deleteById(id);
    }

    @Override
    public int showById(Long id) {
        return visitDao.showById(id);
    }

    @Override
    public int shadowById(Long id) {
        return visitDao.shadowById(id);
    }

    @Override
    public Visit findById(Long id) {
        return visitDao.findById(id);
    }

    @Override
    public List<Visit> findByCondition(VisitQuery query) {
        return visitDao.findByCondition(query);
    }

    @Override
    public List<Visit> findByCondition(Page page, VisitQuery query) {
        return visitDao.findByCondition(page, query);
    }

    @Override
    public PageBean<VisitVO> findByPage(Integer pageNo, Integer pageSize) {
        int Commod = visitDao.findByPageCount();
        PageBean<VisitVO> pager = new PageBean<>();
        pager.setPageNo(pageNo);
        pager.setRows(Integer.valueOf(Commod));
        pager.setPageSize(pageSize);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<VisitVO> list = visitDao.findByPage(pager);
        pager.setLists(list);
        return pager;
    }

    @Override
    public PageBean<VisitVO> getVisit(Integer pageNo, Integer pageSize, String name) {
        PageBean<VisitVO> pager = new PageBean<>();
        pager.setPageNo(pageNo);
        pager.setPageSize(pageSize);
        int first = (pager.getPageNo().intValue() - 1) * pager.getPageSize().intValue();
        pager.setStart(Integer.valueOf(first));
        List<VisitVO> list = visitDao.getVisit(pager, name);
        pager.setLists(list);
        return pager;
    }
}
