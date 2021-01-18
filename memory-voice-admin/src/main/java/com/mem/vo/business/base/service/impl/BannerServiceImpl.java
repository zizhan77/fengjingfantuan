package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.BannerDao;
import com.mem.vo.business.base.model.po.Banner;
import com.mem.vo.business.base.model.po.BannerQuery;
import com.mem.vo.business.base.service.BannerService;
import com.mem.vo.common.dto.Page;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>BannerService<br>
 */
@Configuration
@EnableScheduling
@Service("bannerService")
public class BannerServiceImpl implements BannerService {

    private final static Logger log = LogManager.getLogger(BannerServiceImpl.class);


    @Resource
    private BannerDao bannerDao;

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void bannerLineFlag() {
        List<Banner> banners = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, calendar.get(11) + 1);
        String nextTime = format.format(calendar.getTime());
        String nowTime = format.format(new Date());
        List<Integer> online = bannerDao.getOnLineFlag(nowTime, nextTime);
        if (online != null && online.size() > 0) {
            bannerDao.updatelineFlag(online, 1);
        }
        List<Integer> offline = bannerDao.getOffLineFlag(nowTime, nextTime);
        if (offline != null && offline.size() > 0) {
            bannerDao.updatelineFlag(offline, 0);
        }
    }

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
    public Page<Banner> findPageByCondition(Page<Banner> page, BannerQuery query) {

        List<Banner> list = bannerDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
