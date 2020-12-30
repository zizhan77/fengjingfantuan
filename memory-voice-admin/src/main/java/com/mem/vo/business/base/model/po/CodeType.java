package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class CodeType {
    private Integer id;

    private String name;

    private String des;

    private Date createTime;

    private Integer sponsorId;

    private String url;

    private Integer count;
}
