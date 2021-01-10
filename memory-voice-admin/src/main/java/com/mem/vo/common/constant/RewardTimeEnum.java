package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RewardTimeEnum {

    LOW(Integer.valueOf(1), "低成本"),
    MID(Integer.valueOf(1), "中高成本"),
    HIGH(Integer.valueOf(1), "高成本"),
    ONCE(Integer.valueOf(1), "只能中奖一次");

    private Integer Code;

    private String name;
}
