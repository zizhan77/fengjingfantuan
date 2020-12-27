package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.SponsorDao;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.SponsorQuery;
import com.mem.vo.business.base.service.SponsorService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/**
*
* <br>
* <b>功能：</b>SponsorService<br>
*/
@Service("sponsorService")
public class  SponsorServiceImpl implements SponsorService {
    private final static Logger log= LogManager.getLogger(SponsorServiceImpl.class);


    @Resource
    private SponsorDao sponsorDao;

    @Override
    public int insert(Sponsor sponsor){
        return sponsorDao.insert(sponsor);
    }

    @Override
    public int updateById(Sponsor sponsor){
        return sponsorDao.updateById(sponsor);
    }

    @Override
    public int deleteById(Long id){
        return sponsorDao.deleteById(id);
    }

    @Override
    public Sponsor findById(Long id){
        return sponsorDao.findById(id);
    }

    @Override
    public List<Sponsor> findByCondition(SponsorQuery query){
        return sponsorDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page, SponsorQuery query){
        sponsorDao.findByCondition(page,query);
    }
}
