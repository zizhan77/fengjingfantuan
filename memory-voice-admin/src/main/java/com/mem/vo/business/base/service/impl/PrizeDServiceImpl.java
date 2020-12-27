package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.PrizeDDao;
import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.PrizeDQuery;
import com.mem.vo.business.base.service.PrizeDService;
import com.mem.vo.common.dto.Page;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
*
* <br>
* <b>功能：</b>PrizeDService<br>
*/
@Service("prizeDService")
@Slf4j
public class  PrizeDServiceImpl implements PrizeDService {


    @Resource
    private PrizeDDao prizeDDao;

    @Override
    public int insert(PrizeD prizeD){
        return prizeDDao.insert(prizeD);
    }

    @Override
    public int insertBatch(List<PrizeD> prizeDQuery) {
        return prizeDDao.insertBatch(prizeDQuery);
    }

    @Override
    public int updateById(PrizeD prizeD){
        return prizeDDao.updateById(prizeD);
    }

    @Override
    public int deleteById(List<Long> ids){
        return prizeDDao.deleteById(ids);
    }

    @Override
    public PrizeD findById(Long id){
        return prizeDDao.findById(id);
    }

    @Override
    public List<PrizeD> findByCondition(PrizeDQuery query){
        return prizeDDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page,PrizeDQuery query){
        prizeDDao.findByCondition(page,query);
    }

    @Override
    public List<Integer> selectIdByEt(PrizeDQuery query) {
        return prizeDDao.selectIdByEt(query);
    }
}
