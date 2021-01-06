package com.mem.vo.business.biz.model.dto;

import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.User;
import lombok.Data;

@Data
public class CommonLoginContextBuilder {
    private String sourceCode;

    private String bizCode;

    private String sessionKey;

    private Integer status;

    private Long userId;

    private User user;

    private Organizer organizer;

    private Sponsor sponsor;
}
