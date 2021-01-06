package com.mem.vo.business.biz.model.vo.basic;

import lombok.Data;

import java.util.List;

@Data
public class BasicAddressNodeBuilder {
    private String addressCode;

    private String addressName;

    private String parentCode;

    private Integer level;

    private List<BasicAddressNode> children;
}
