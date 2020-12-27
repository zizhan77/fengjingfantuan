package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.UserDao;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserQuery;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>UserService<br>
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final static Logger log = LogManager.getLogger(UserServiceImpl.class);


    @Resource
    private UserDao userDao;

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public int updateById(User user) {
        return userDao.updateById(user);
    }

    @Override
    public int deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findByCondition(UserQuery query) {
        return userDao.findByCondition(query);
    }

    @Override
    public Page<User> findPageByCondition(Page page, UserQuery query) {
        List<User> users = userDao.findByCondition(page, query);
        page.setData(users);
        return page;
    }

    @Override
    public int updateBySourceAndBizCode(String phoneNumber, String sourceCode, String bizCode,String userName) {
        return userDao.updateBySourceAndBizCode(phoneNumber,sourceCode,bizCode,userName);
    }
}
