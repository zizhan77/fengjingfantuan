package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/1 14:29
 */

@AllArgsConstructor
@Getter
public enum BannerType {

    BANNER(1, "banner"),
    OPENING_PAGE(2, "开屏页"),
    INDEX_PAGE(3, "首页图"),
    POPUP_PAGE(4, "弹窗图"),
        ;

    private Integer code;
    private String name;


}
