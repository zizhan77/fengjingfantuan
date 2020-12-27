package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.CouponRecordDao;
import com.mem.vo.business.base.model.po.CouponRecord;
import com.mem.vo.business.base.model.po.CouponRecordQuery;
import com.mem.vo.business.base.service.CouponRecordService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/**
*
* <br>
* <b>功能：</b>CouponRecordService<br>
*/
@Service("couponRecordService")
public class  CouponRecordServiceImpl implements CouponRecordService {
    private final static Logger log= LogManager.getLogger(CouponRecordServiceImpl.class);


    @Resource
    private CouponRecordDao couponRecordDao;

    @Override
    public int insert(CouponRecord couponRecord){
        return couponRecordDao.insert(couponRecord);
    }

    @Override
    public int updateById(CouponRecord couponRecord){
        return couponRecordDao.updateById(couponRecord);
    }

    @Override
    public int deleteById(Long id){
        return couponRecordDao.deleteById(id);
    }

    @Override
    public CouponRecord findById(Long id){
        return couponRecordDao.findById(id);
    }

    @Override
    public List<CouponRecord> findByCondition(CouponRecordQuery query){
        return couponRecordDao.findByCondition(query);
    }

    @Override
    public List<CouponRecord> findPageByCondition(Page page, CouponRecordQuery query){
        return couponRecordDao.findByCondition(page,query);
    }
}
