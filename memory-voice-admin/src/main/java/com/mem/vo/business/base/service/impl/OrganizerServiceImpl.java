package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.dao.OrganizerDao;
import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.business.base.model.po.OrganizerExample;
import com.mem.vo.business.base.service.OrganizerService;
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
}
