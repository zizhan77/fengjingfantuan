package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/5 0:35
 */
@AllArgsConstructor
@Getter
public enum RoleType {

    SUPER_ADMIN(1, "超级管理员"),
    ADMIN(2, "管理员");
    private Integer code;
    private String name;
}
