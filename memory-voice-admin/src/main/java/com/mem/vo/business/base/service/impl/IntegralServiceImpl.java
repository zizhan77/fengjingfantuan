package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.IntegralDao;
import com.mem.vo.business.base.model.po.Integral;
import com.mem.vo.business.base.model.po.IntegralQuery;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.service.IntegralService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/**
*
* <br>
* <b>功能：</b>IntegralService<br>
*/
@Service("integralService")
public class  IntegralServiceImpl implements IntegralService {
    private final static Logger log= LogManager.getLogger(IntegralServiceImpl.class);


    @Resource
    private IntegralDao integralDao;
    @Resource
    private UserService userService;

    @Override
    public int insert(Integral integral){
        //每次插入积分表，都要更新用户积分
        User user = userService.findById(Long.valueOf(integral.getUserId()));
        user.setIntegral(user.getIntegral()+integral.getIntegralQty());
        userService.updateById(user);
        return integralDao.insert(integral);
    }

    @Override
    public int updateById(Integral integral){
        return integralDao.updateById(integral);
    }

    @Override
    public int deleteById(Long id){
        return integralDao.deleteById(id);
    }

    @Override
    public Integral findById(Long id){
        return integralDao.findById(id);
    }

    @Override
    public List<Integral> findByCondition(IntegralQuery query){
        return integralDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page,IntegralQuery query){
        integralDao.findByCondition(page,query);
    }
}
