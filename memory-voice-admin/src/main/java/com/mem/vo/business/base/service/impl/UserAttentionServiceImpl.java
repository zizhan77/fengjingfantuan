package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.UserAttentionDao;
import com.mem.vo.business.base.model.po.UserAttention;
import com.mem.vo.business.base.model.po.UserAttentionQuery;
import com.mem.vo.business.base.service.UserAttentionService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>UserAttentionService<br>
 */
@Service("userAttentionService")
public class UserAttentionServiceImpl implements UserAttentionService {

    private final static Logger log = LogManager.getLogger(UserAttentionServiceImpl.class);


    @Resource
    private UserAttentionDao userAttentionDao;

    @Override
    public int insert(UserAttention userAttention) {
        return userAttentionDao.insert(userAttention);
    }

    @Override
    public int updateById(UserAttention userAttention) {
        return userAttentionDao.updateById(userAttention);
    }

    @Override
    public int deleteById(Long id) {
        return userAttentionDao.deleteById(id);
    }

    @Override
    public UserAttention findById(Long id) {
        return userAttentionDao.findById(id);
    }

    @Override
    public List<UserAttention> findByCondition(UserAttentionQuery query) {
        return userAttentionDao.findByCondition(query);
    }

    @Override
    public Page<UserAttention> findPageByCondition(Page page, UserAttentionQuery query) {
        List<UserAttention> list = userAttentionDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
