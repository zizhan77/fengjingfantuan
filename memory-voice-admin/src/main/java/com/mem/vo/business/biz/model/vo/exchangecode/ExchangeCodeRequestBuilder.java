package com.mem.vo.business.biz.model.vo.exchangecode;

import lombok.Data;

import java.util.List;

@Data
public class ExchangeCodeRequestBuilder {
    private String businessKey;

    private List<String> recordBusinessKeyList;

    private Integer businessTag;

    private String remark;

    private Integer number;
}
