package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrizeDMiniTypeEnum {
    NO(0, "0成本"),
    LOW(1, "低成本"),
    MID(2, "中高成本"),
    HIGH(3, "高成本"),
    ONCE(4, "只能中奖一次");

    private Integer Code;

    private String name;

}
