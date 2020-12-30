package com.mem.vo.business.base.model.po;

import lombok.Data;

@Data
public class ChangeCode {
    private Integer id;

    private String key;

    private String password;

    private String isChange;

    private Integer codeType;
}
