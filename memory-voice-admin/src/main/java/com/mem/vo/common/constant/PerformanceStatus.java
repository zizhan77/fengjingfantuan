package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/1 11:00
 */

@Getter
@AllArgsConstructor
public enum PerformanceStatus {

    PRE_SALE_WITHOUT_TIME(1, "预售(未确定开售时间)"),
    PRE_SALE_HAS_TIME(2, "预售(已确定开售时间)"),
    SALEING(3, "开售"),
    HAS_EXPIRED(4, "已过期");
    private Integer code;
    private String desc;

    public static String getNameByCode(Integer code) {

        for (PerformanceStatus status : PerformanceStatus.values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return "";
    }
    public static Set<Integer> wxEnableSet = new HashSet<>();
    static {
        wxEnableSet.add(PRE_SALE_WITHOUT_TIME.getCode());
        wxEnableSet.add(PRE_SALE_HAS_TIME.getCode());
        wxEnableSet.add(SALEING.getCode());

    }


}
