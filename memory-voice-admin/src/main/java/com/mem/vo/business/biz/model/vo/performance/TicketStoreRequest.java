package com.mem.vo.business.biz.model.vo.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by litongwei on 2019/6/23.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketStoreRequest {

    private Integer limit=10;
    private Integer page;
    private Long performanceId; //演出id
}
