package com.mem.vo.business.biz.model.dto;

import com.mem.vo.business.base.model.po.Organizer;
import com.mem.vo.business.base.model.po.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/30 10:08
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonLoginContext {

    private String sourceCode;

    private String bizCode;

    private String sessionKey;

    private Integer status;

    private Long userId;

    private User user;
//增加的
    private Organizer organizer;

}
