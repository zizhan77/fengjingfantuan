package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.DrawRecordDao;
import com.mem.vo.business.base.model.po.DrawRecord;
import com.mem.vo.business.base.model.po.DrawRecordQuery;
import com.mem.vo.business.base.service.DrawRecordService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>DrawRecordService<br>
 */
@Service("drawRecordService")
public class DrawRecordServiceImpl implements DrawRecordService {

    private final static Logger log = LogManager.getLogger(DrawRecordServiceImpl.class);


    @Resource
    private DrawRecordDao drawRecordDao;

    @Override
    public int insert(DrawRecord drawRecord) {
        return drawRecordDao.insert(drawRecord);
    }

    @Override
    public int updateById(DrawRecord drawRecord) {
        return drawRecordDao.updateById(drawRecord);
    }

    @Override
    public int deleteById(Long id) {
        return drawRecordDao.deleteById(id);
    }

    @Override
    public DrawRecord findById(Long id) {
        return drawRecordDao.findById(id);
    }

    @Override
    public List<DrawRecord> findByCondition(DrawRecordQuery query) {
        return drawRecordDao.findByCondition(query);
    }

    @Override
    public Page<DrawRecord> findPageByCondition(Page page, DrawRecordQuery query) {
        List<DrawRecord> list = drawRecordDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
