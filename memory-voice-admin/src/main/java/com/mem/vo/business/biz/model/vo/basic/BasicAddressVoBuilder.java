package com.mem.vo.business.biz.model.vo.basic;

import lombok.Data;

import java.util.Date;

@Data
public class BasicAddressVoBuilder {
    private Long id;

    private String addressCode;

    private String addressName;

    private String parentCode;

    private Integer level;

    private Integer status;

    private Date createTime;
}
