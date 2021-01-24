package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.OrganizerDao;
import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.business.base.model.po.OrganizerExample;
import com.mem.vo.business.base.service.OrganizerService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("organizerService")
@Transactional
public class OrganizerServiceImpl implements OrganizerService {
    @Resource
    private OrganizerDao organizerDao;

    @Override
    public Organizer findById(Long id) {
        return organizerDao.queryById(id);
    }

    @Override
    public List<Organizer> queryByUsername(String username) {

        return organizerDao.selectByUsername(username);
    }

    @Override
    public Organizer queryById(Long userId) {
        return organizerDao.queryById(userId);
    }

    @Override
    public Organizer saveOrUpdate(Organizer organizer) {
        BizAssert.notNull(organizer, BizCode.PARAM_NULL.getMessage());
        if (organizer.getId() == null || "".equals(organizer.getId())) {
            organizer.setIsDelete(0);
            int insert = organizerDao.insert(organizer);
            if (insert == 0) {
                throw new BizException("添加主办方失败");
            }
            return organizer;
        }
        int i = organizerDao.update(organizer);
        if (i == 0) {
            throw new BizException("修改主办方失败");
        }
        return organizer;
    }

    @Override
    public Page queryAll(Page page) {
        List<Organizer> list = organizerDao.queryPage(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<Organizer> queryBy(Organizer organizer) {
        BizAssert.notNull(organizer, BizCode.PARAM_NULL.getMessage());
        return this.organizerDao.queryBy(organizer);
    }
}
