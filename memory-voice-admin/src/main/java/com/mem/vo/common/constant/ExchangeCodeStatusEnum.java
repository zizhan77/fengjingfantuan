package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 兑换码接口
 * Created by litongwei on 2019/6/30.
 */

@Getter
@AllArgsConstructor
public enum  ExchangeCodeStatusEnum {

    ON("1","启用"),
    OFF("2","停用");
    private String code;
    private String name;

}
