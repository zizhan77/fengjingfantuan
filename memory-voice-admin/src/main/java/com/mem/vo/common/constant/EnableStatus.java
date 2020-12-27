package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by litongwei on 2019/7/3.
 */

@AllArgsConstructor
@Getter
public enum  EnableStatus {

    ON(1,"启用"),
    OFF(2,"停用");

    private Integer code;

    private String name;
}
