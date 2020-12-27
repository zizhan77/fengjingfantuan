package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Organizer;

import java.util.List;

public interface OrganizerService {
    Organizer findById(Long id) ;


    List<Organizer> queryByUsername(String username);

    Organizer queryById(Long userId);
}
