package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BannerUrlType {
    ACTIVITY(1, "活动"),
    PERFORMANCE(2, "演出");

    private Integer code;

    private String name;

}
