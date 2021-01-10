package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActivityQaEnum {
    QA(0, "问题"),
    URL(1, "图片");

    private Integer Code;

    private String name;
}
