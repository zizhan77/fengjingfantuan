package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.PictureDao;
import com.mem.vo.business.base.model.po.Picture;
import com.mem.vo.business.base.model.po.PictureQuery;
import com.mem.vo.business.base.service.PictureService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>PictureService<br>
 */
@Service("pictureService")
public class PictureServiceImpl implements PictureService {

    private final static Logger log = LogManager.getLogger(PictureServiceImpl.class);


    @Resource
    private PictureDao pictureDao;

    @Override
    public int insert(Picture picture) {
        return pictureDao.insert(picture);
    }

    @Override
    public int updateById(Picture picture) {
        return pictureDao.updateById(picture);
    }

    @Override
    public int deleteById(Long id) {
        return pictureDao.deleteById(id);
    }

    @Override
    public Picture findById(Long id) {
        return pictureDao.findById(id);
    }

    @Override
    public List<Picture> findByCondition(PictureQuery query) {
        return pictureDao.findByCondition(query);
    }

    @Override
    public Page<Picture> findPageByCondition(Page page, PictureQuery query) {
        List<Picture> list = pictureDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
