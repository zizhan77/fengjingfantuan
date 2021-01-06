package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-07 00:01
 */
@AllArgsConstructor
@Getter
public enum  RewardEnum {

    STATUS_AVAILABLE(0,"未兑换"),
    STATUS_USED(1, "已兑换"),
    STATUS_GIVEUP(2, "放弃");
    private Integer code;
    private String message;
}
