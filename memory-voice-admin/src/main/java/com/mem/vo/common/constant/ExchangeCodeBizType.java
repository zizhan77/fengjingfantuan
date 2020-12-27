package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description
 * @date 2019/6/27 14:55
 */

@AllArgsConstructor
@Getter
public enum  ExchangeCodeBizType {

    FOR_COUPON(1,"优惠券兑换码"),
    FOR_PRIZE(2,"奖品兑换码"),
    FOR_TICKET(3,"电子票兑换码"),
    FOR_SEAT_TICKET(4,"有座电子票兑换码tempstatus"),
    FOR_NOSEAT_TICKET(5,"无座电子票兑换码tempstatus");
    private Integer code;
    private String desc;
}
