package com.mem.vo.business.base.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.mem.vo.business.base.dao.AuthorityRoleDao;
import com.mem.vo.business.base.model.po.AuthorityRole;
import com.mem.vo.business.base.model.po.AuthorityRoleQuery;
import com.mem.vo.business.base.service.AuthorityRoleService;
import com.mem.vo.common.dto.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * <br>
 * <b>功能：</b>AuthorityRoleService<br>
 */
@Service("authorityRoleService")
public class AuthorityRoleServiceImpl implements AuthorityRoleService {

    private final static Logger log = LogManager.getLogger(AuthorityRoleServiceImpl.class);


    @Resource
    private AuthorityRoleDao authorityRoleDao;

    @Override
    public int insert(AuthorityRole authorityRole) {
        return authorityRoleDao.insert(authorityRole);
    }

    @Override
    public int updateById(AuthorityRole authorityRole) {
        return authorityRoleDao.updateById(authorityRole);
    }

    @Override
    public int deleteById(Long id) {
        return authorityRoleDao.deleteById(id);
    }

    @Override
    public AuthorityRole findById(Long id) {
        return authorityRoleDao.findById(id);
    }

    @Override
    public List<AuthorityRole> findByCondition(AuthorityRoleQuery query) {
        return authorityRoleDao.findByCondition(query);
    }

    @Override
    public Page<AuthorityRole> findPageByCondition(Page page, AuthorityRoleQuery query) {
        List<AuthorityRole> list = authorityRoleDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Override
    public int updateByRoleIds(List<Integer> roleIds, String updateUser) {
        return authorityRoleDao.updateByRoleIds(roleIds,updateUser);
    }
}
