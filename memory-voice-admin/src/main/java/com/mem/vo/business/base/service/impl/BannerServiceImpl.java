package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.BannerDao;
import com.mem.vo.business.base.model.po.Banner;
import com.mem.vo.business.base.model.po.BannerQuery;
import com.mem.vo.business.base.service.BannerService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>BannerService<br>
 */
@Service("bannerService")
public class BannerServiceImpl implements BannerService {

    private final static Logger log = LogManager.getLogger(BannerServiceImpl.class);


    @Resource
    private BannerDao bannerDao;

    @Override
    public int insert(Banner banner) {
        return bannerDao.insert(banner);
    }

    @Override
    public int updateById(Banner banner) {
        return bannerDao.updateById(banner);
    }

    @Override
    public int deleteById(Long id) {
        return bannerDao.deleteById(id);
    }

    @Override
    public Banner findById(Long id) {
        return bannerDao.findById(id);
    }

    @Override
    public List<Banner> findByCondition(BannerQuery query) {
        return bannerDao.findByCondition(query);
    }

    @Override
    public Page<Banner> findPageByCondition(Page page, BannerQuery query) {

        List<Banner> list = bannerDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
