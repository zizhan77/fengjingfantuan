package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description
 * @date 2019/7/4 17:14
 */

@Getter
@AllArgsConstructor
public enum  UserOperateEnum {

    LOGIN(1,"登录"),
    CREATE_ORDER(2,"创建订单"),
    PAY(3,"支付");

    private Integer code;
    private String desc;

}
