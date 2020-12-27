package com.mem.vo.business.biz.model.vo.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/9 19:53
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicPlaceRequest {

    private Integer limit = 10;  //条数

    private Integer page; //页数

    private String placeName;

    private String cityId;

    private Integer enable;

}
