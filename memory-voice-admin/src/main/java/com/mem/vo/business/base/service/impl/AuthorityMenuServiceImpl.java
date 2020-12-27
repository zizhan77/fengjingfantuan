package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.AuthorityMenuDao;
import com.mem.vo.business.base.model.po.AuthorityMenu;
import com.mem.vo.business.base.model.po.AuthorityMenuQuery;
import com.mem.vo.business.base.service.AuthorityMenuService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>AuthorityMenuService<br>
 */
@Service("authorityMenuService")
public class AuthorityMenuServiceImpl implements AuthorityMenuService {

    private final static Logger log = LogManager.getLogger(AuthorityMenuServiceImpl.class);


    @Resource
    private AuthorityMenuDao authorityMenuDao;

    @Override
    public int insert(AuthorityMenu authorityMenu) {
        return authorityMenuDao.insert(authorityMenu);
    }

    @Override
    public int updateById(AuthorityMenu authorityMenu) {
        return authorityMenuDao.updateById(authorityMenu);
    }

    @Override
    public int deleteById(Long id) {
        return authorityMenuDao.deleteById(id);
    }

    @Override
    public AuthorityMenu findById(Long id) {
        return authorityMenuDao.findById(id);
    }

    @Override
    public List<AuthorityMenu> findByCondition(AuthorityMenuQuery query) {
        return authorityMenuDao.findByCondition(query);
    }

    @Override
    public Page<AuthorityMenu> findPageByCondition(Page page, AuthorityMenuQuery query) {
        List<AuthorityMenu> list = authorityMenuDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
