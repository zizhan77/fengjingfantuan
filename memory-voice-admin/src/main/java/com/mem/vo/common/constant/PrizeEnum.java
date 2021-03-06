package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-24 22:52
 */
@Getter
@AllArgsConstructor
public enum PrizeEnum {
    TICKET(1, "门票"),
    MATERIAL(2, "实物"),
    INTEGRAL(3, "饭团"),
    coupon(4, "优惠券"),
    NO_CHANGE(0, "未兑换"),
    YES_CHANGE(1, "已兑换"),
    DEFAULT_PRIZE(100,"默认返回的饭团,完善个人信息增加的饭团");


    private Integer Code;
    private String name;
}
