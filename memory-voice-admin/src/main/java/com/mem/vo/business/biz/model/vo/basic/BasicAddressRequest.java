package com.mem.vo.business.biz.model.vo.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/10 23:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicAddressRequest {

    String cityName;
    private Integer limit = 10;  //条数
    private Integer page; //页数
    private Integer status;


}
