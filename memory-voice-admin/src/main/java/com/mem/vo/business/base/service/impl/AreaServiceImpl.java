package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.AreaDao;
import com.mem.vo.business.base.model.po.Area;
import com.mem.vo.business.base.model.po.AreaQuery;
import com.mem.vo.business.base.service.AreaService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>AreaService<br>
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

    private final static Logger log = LogManager.getLogger(AreaServiceImpl.class);


    @Resource
    private AreaDao areaDao;

    @Override
    public int insert(Area area) {
        return areaDao.insert(area);
    }

    @Override
    public int updateById(Area area) {
        return areaDao.updateById(area);
    }

    @Override
    public int deleteById(Long id) {
        return areaDao.deleteById(id);
    }

    @Override
    public Area findById(Long id) {
        return areaDao.findById(id);
    }

    @Override
    public List<Area> findByCondition(AreaQuery query) {
        return areaDao.findByCondition(query);
    }

    @Override
    public Page<Area> findPageByCondition(Page page, AreaQuery query) {
        List<Area> list = areaDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
