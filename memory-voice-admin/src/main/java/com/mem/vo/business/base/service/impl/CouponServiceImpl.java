package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.CouponDao;
import com.mem.vo.business.base.model.po.Coupon;
import com.mem.vo.business.base.model.po.CouponQuery;
import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.service.CouponService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
*
* <br>
* <b>功能：</b>CouponService<br>
*/
@Service("couponService")
public class  CouponServiceImpl implements CouponService {
    private final static Logger log= LogManager.getLogger(CouponServiceImpl.class);


    @Resource
    private CouponDao couponDao;

    @Override
    public int insert(Coupon coupon){
        return couponDao.insert(coupon);
    }

    @Override
    public int updateById(Coupon coupon){
        return couponDao.updateById(coupon);
    }

    @Override
    public int deleteById(Long id){
        return couponDao.deleteById(id);
    }

    @Override
    public Coupon findById(Long id){
        return couponDao.findById(id);
    }

    @Override
    public List<Coupon> findByCondition(CouponQuery query){
        return couponDao.findByCondition(query);
    }

    @Override
    public Page<Coupon> findPageByCondition(Page page, CouponQuery query){
        List<Coupon> list = couponDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
