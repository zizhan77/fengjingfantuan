package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.common.dto.Page;
import java.util.List;

public interface OrganizerService {
    Organizer findById(Long id) ;


    List<Organizer> queryByUsername(String username);

    Organizer queryById(Long userId);

    Organizer saveOrUpdate(Organizer organizer);

    Page queryAll(Page page);

    List<Organizer> queryBy(Organizer organizer);
}
