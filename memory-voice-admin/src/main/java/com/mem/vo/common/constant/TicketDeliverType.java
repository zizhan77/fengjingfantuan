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
public enum TicketDeliverType {

    Express(10, "快递"),
    Eticket(20, "电子票");


    private Integer code;
    private String desc;

    public static String getNameByCode(Integer code) {

        for (TicketDeliverType status : TicketDeliverType.values()) {
            if (status.getCode().equals(code)) {
                return status.getDesc();
            }
        }
        return "";
    }

}
