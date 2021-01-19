package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-07 23:24
 */
@Getter
@AllArgsConstructor
public enum  SponsorEnum {
    ON(1,"启用"),
    OFF(2,"停用");

    private Integer code;

    private String name;
}
