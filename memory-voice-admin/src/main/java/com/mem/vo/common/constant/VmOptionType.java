package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 14:25
 */

@AllArgsConstructor
@Getter
public enum VmOptionType {

    INSERT(1, "新增"),
    UPDATE(2, "修改");

    private Integer code;

    private String name;


}

