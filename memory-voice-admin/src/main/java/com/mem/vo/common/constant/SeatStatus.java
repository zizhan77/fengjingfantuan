package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author baiyuehui
 * @description:
 * @date 2019/5/31 14:53
 */
@AllArgsConstructor
@Getter
public enum SeatStatus {
    UNABLE(0, "间隔"),
    ONSALE(1, "已开启"),
    CLOSE(2, "已关闭"),
    SELLOUT(3, "已售出");

    //端编码
    private Integer code;

    private String name;


}
