package com.mem.vo.business.biz.model.vo.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 15:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowDetail {

    private PerformanceVo performanceVo;

    private BasicPlaceVo placeInfo;

}
