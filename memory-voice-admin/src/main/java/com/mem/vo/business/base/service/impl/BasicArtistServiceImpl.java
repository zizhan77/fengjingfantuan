package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.BasicArtistDao;
import com.mem.vo.business.base.model.po.BasicArtist;
import com.mem.vo.business.base.model.po.BasicArtistQuery;
import com.mem.vo.business.base.model.vo.PlaceArtistVO;
import com.mem.vo.business.base.service.BasicArtistService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>BasicArtistService<br>
 */
@Service("basicArtistService")
public class BasicArtistServiceImpl implements BasicArtistService {

    private final static Logger log = LogManager.getLogger(BasicArtistServiceImpl.class);


    @Resource
    private BasicArtistDao basicArtistDao;

    @Override
    public int insert(BasicArtist basicArtist) {
        String artistName = basicArtist.getArtistName();
        int list = basicArtistDao.findByArtistName(artistName);
        if (list != 0) {
            throw new BizException("这个艺人已经不存在了");
        }
        return basicArtistDao.insert(basicArtist);
    }

    @Override
    public int updateById(BasicArtist basicArtist) {
        return basicArtistDao.updateById(basicArtist);
    }

    @Override
    public int deleteById(Long id) {
        return basicArtistDao.deleteById(id);
    }

    @Override
    public BasicArtist findById(Long id) {
        return basicArtistDao.findById(id);
    }

    @Override
    public List<BasicArtist> findByCondition(BasicArtistQuery query) {
        return basicArtistDao.findByCondition(query);
    }

    @Override
    public Page<BasicArtist> findByName(Page page, String name) {
        List<BasicArtist> list = basicArtistDao.findByName(page, name);
        page.setData(list);
        return page;
    }

    @Override
    public Page<BasicArtist> findPageByCondition(Page page, BasicArtistQuery query) {
        List<BasicArtist> list = basicArtistDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Override
    public List<PlaceArtistVO> findByIdList(List<String> artistList) {
        BizAssert.notEmpty(artistList, BizCode.PARAM_NULL.getMessage());
        return basicArtistDao.findByIdList(artistList);
    }
}
