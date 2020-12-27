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
    TICKET(1, "电子票"),
    MATERIAL(2, "实物"),
    INTEGRAL(3, "积分"),
    NO_CHANGE(0, "未兑换"),
    YES_CHANGE(1, "已兑换"),
    DEFAULT_PRIZE(100,"默认返回的点数,完善个人信息增加的点数");
    private Integer Code;
    private String name;
}
