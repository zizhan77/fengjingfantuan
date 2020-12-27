package com.mem.vo.business.biz.service.login;

import com.mem.vo.business.base.model.po.Organizer;

import com.mem.vo.business.biz.model.dto.OrganizerLoginRequest;

public interface OrganizerLoginService {
    Organizer checkPasswd(OrganizerLoginRequest organizerLoginRequest);

    String getToken(OrganizerLoginRequest organizerLoginRequest, Organizer organizer);

    boolean loginOut(String token);
}
