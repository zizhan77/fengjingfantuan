package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.UserAddressDao;
import com.mem.vo.business.base.model.po.UserAddress;
import com.mem.vo.business.base.model.po.UserAddressQuery;
import com.mem.vo.business.base.service.UserAddressService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>UserAddressService<br>
 */
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

    private final static Logger log = LogManager.getLogger(UserAddressServiceImpl.class);


    @Resource
    private UserAddressDao userAddressDao;

    @Override
    public int insert(UserAddress userAddress) {
        return userAddressDao.insert(userAddress);
    }

    @Override
    public int updateById(UserAddress userAddress) {
        return userAddressDao.updateById(userAddress);
    }

    @Override
    public int deleteById(Long id) {
        return userAddressDao.deleteById(id);
    }

    @Override
    public UserAddress findById(Long id) {
        return userAddressDao.findById(id);
    }


    @Override
    public List<UserAddress> findByCondition(UserAddressQuery query) {
        return userAddressDao.findByCondition(query);
    }

    @Override
    public Page<UserAddress> findPageByCondition(Page page, UserAddressQuery query) {
        List<UserAddress> list = userAddressDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
