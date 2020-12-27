package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.AuthorityRoleMenuRelDao;
import com.mem.vo.business.base.model.po.AuthorityRoleMenuRel;
import com.mem.vo.business.base.model.po.AuthorityRoleMenuRelQuery;
import com.mem.vo.business.base.service.AuthorityRoleMenuRelService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>AuthorityRoleMenuRelService<br>
 */
@Service("authorityRoleMenuRelService")
public class AuthorityRoleMenuRelServiceImpl implements AuthorityRoleMenuRelService {

    private final static Logger log = LogManager.getLogger(AuthorityRoleMenuRelServiceImpl.class);


    @Resource
    private AuthorityRoleMenuRelDao authorityRoleMenuRelDao;

    @Override
    public int insert(AuthorityRoleMenuRel authorityRoleMenuRel) {
        return authorityRoleMenuRelDao.insert(authorityRoleMenuRel);
    }

    @Override
    public int updateById(AuthorityRoleMenuRel authorityRoleMenuRel) {
        return authorityRoleMenuRelDao.updateById(authorityRoleMenuRel);
    }

    @Override
    public int deleteById(Long id) {
        return authorityRoleMenuRelDao.deleteById(id);
    }

    @Override
    public AuthorityRoleMenuRel findById(Long id) {
        return authorityRoleMenuRelDao.findById(id);
    }

    @Override
    public List<AuthorityRoleMenuRel> findByCondition(AuthorityRoleMenuRelQuery query) {
        return authorityRoleMenuRelDao.findByCondition(query);
    }

    @Override
    public Page<AuthorityRoleMenuRel> findPageByCondition(Page page, AuthorityRoleMenuRelQuery query) {
        List<AuthorityRoleMenuRel> list = authorityRoleMenuRelDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }
}
