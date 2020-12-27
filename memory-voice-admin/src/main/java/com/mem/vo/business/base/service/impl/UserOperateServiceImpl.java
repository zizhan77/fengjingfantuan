package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.UserOperateDao;
import com.mem.vo.business.base.model.po.UserOperate;
import com.mem.vo.business.base.model.po.UserOperateQuery;
import com.mem.vo.business.base.service.UserOperateService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>UserOperateService<br>
 */
@Service("userOperateService")
public class UserOperateServiceImpl implements UserOperateService {

    private final static Logger log = LogManager.getLogger(UserOperateServiceImpl.class);


    @Resource
    private UserOperateDao userOperateDao;

    @Override
    public int insert(UserOperate userOperate) {
        return userOperateDao.insert(userOperate);
    }

    @Override
    public int updateById(UserOperate userOperate) {
        return userOperateDao.updateById(userOperate);
    }

    @Override
    public int deleteById(Long id) {
        return userOperateDao.deleteById(id);
    }

    @Override
    public UserOperate findById(Long id) {
        return userOperateDao.findById(id);
    }

    @Override
    public List<UserOperate> findByCondition(UserOperateQuery query) {
        return userOperateDao.findByCondition(query);
    }

    @Override
    public Page<UserOperate> findPageByCondition(Page page, UserOperateQuery query) {
        List<UserOperate> list = userOperateDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
