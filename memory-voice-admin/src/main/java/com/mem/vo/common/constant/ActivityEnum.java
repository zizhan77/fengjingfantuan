package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-22 16:58
 */
@AllArgsConstructor
@Getter
public enum ActivityEnum {
    BASE_LOTTERY_QTY(5,"基础摇奖次数"),
    PERSON_SHARE_LIMIT(20,"每日用户最多拉取人数上限"),
    GROUP_SHARE_LIMIT(1,"每日用户最多分享群上限"),
    MINIMUM_PASS_QTY(12,"最小通关数，通关此数量到达老虎机摇奖"),
    ACTIVITY_USER_INFO(0,"完善个人信息");
    private Integer Code;
    private String name;
}
