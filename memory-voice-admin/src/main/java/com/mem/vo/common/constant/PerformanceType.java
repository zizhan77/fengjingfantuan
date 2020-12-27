package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/5 14:20
 */

@Getter
@AllArgsConstructor
public enum  PerformanceType {

    PerformanceType_1(1, "演唱会"),
    PerformanceType_2(2, "liveHouse");


    private Integer code;
    private String desc;

    public static String getNameByCode(Integer code) {

        for (PerformanceType status : PerformanceType.values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return "";
    }

}
