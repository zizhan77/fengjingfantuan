package com.mem.vo.business.biz.model.vo.basic;

import lombok.Data;

@Data
public class BasicAddressRequestBuilder {
    private String cityName;

    private Integer limit;

    private Integer page;

    private Integer status;
}
