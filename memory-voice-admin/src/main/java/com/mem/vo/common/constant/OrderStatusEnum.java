package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author baiyuehui
 * @description:
 * @date 2019/5/31 14:53
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    CANCELED(-1, "取消订单"),
    WAITPAY(0, "待支付"),
    WAITSEND(10, "待发货"),
    SENDOUT(20, "已发货"),
    FINISHED(30, "已完成");
    //端编码
    private Integer code;

    private String name;


}
