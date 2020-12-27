package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 14:53
 */
@AllArgsConstructor
@Getter
public enum UserStatus {

    ENABLE(1, "启用"),
    UNABLE(3, "禁用");

    //端编码
    private Integer code;

    private String name;


}
