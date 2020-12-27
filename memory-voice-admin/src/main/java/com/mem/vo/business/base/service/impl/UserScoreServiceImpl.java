package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.UserScoreDao;
import com.mem.vo.business.base.model.po.UserScore;
import com.mem.vo.business.base.model.po.UserScoreQuery;
import com.mem.vo.business.base.service.UserScoreService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>UserScoreService<br>
 */
@Service("userScoreService")
public class UserScoreServiceImpl implements UserScoreService {

    private final static Logger log = LogManager.getLogger(UserScoreServiceImpl.class);


    @Resource
    private UserScoreDao userScoreDao;

    @Override
    public int insert(UserScore userScore) {
        return userScoreDao.insert(userScore);
    }

    @Override
    public int updateById(UserScore userScore) {
        return userScoreDao.updateById(userScore);
    }

    @Override
    public int deleteById(Long id) {
        return userScoreDao.deleteById(id);
    }

    @Override
    public UserScore findById(Long id) {
        return userScoreDao.findById(id);
    }

    @Override
    public List<UserScore> findByCondition(UserScoreQuery query) {
        return userScoreDao.findByCondition(query);
    }

    @Override
    public Page<UserScore> findPageByCondition(Page page, UserScoreQuery query) {
        List<UserScore> list = userScoreDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
