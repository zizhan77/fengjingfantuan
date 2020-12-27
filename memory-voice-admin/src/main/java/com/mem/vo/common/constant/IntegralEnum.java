package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-29 21:18
 */
@Getter
@AllArgsConstructor
public enum IntegralEnum {
    TYPE_GET(0,"获得的积分"),
    TYPE_USE(1,"用掉的积分");
    private Integer code;
    private String name;
}
