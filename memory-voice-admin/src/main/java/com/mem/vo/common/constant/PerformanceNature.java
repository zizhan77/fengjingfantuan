package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/9 20:26
 */

@Getter
@AllArgsConstructor
public enum PerformanceNature {

    PerformancenNature_1(1, "赞助商任务+售卖"),
    PerformancenNature_2(2, "纯赞助商任务"),
    PerformancenNature_3(3, "纯售卖");

    private Integer code;
    private String desc;

    public static String getNameByCode(Integer code) {

        for (PerformanceNature status : PerformanceNature.values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return "";
    }
}
