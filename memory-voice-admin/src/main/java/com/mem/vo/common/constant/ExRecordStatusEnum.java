package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * 兑换码记录表状态
 * Created by litongwei on 2019/6/30.
 */
@Getter
@AllArgsConstructor
public enum  ExRecordStatusEnum {

    UN_EXCHANGE("1","未兑换"),
    UN_USE("2","未使用"),
    USED("3","已使用");

    private String code;
    private String name;

    public static String getNameByCode(String code) {

        for (ExRecordStatusEnum status : ExRecordStatusEnum.values()) {
            if (status.getCode().equals(code)) {
                return status.getName();
            }
        }
        return "";
    }

}
