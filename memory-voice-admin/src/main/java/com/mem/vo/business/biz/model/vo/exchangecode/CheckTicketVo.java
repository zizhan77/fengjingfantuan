package com.mem.vo.business.biz.model.vo.exchangecode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by litongwei on 2019/6/30.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckTicketVo {

    /**
     * 演出id
     */
    private Long performanceId;


    /*
     * 兑换码
     */
    private String exchangeCode;

    /**
     * 用户 id
     */
    private Long userId;

    public static CheckTicketVoBuilder builder() {
        return new CheckTicketVoBuilder();
    }

    public static class CheckTicketVoBuilder {
        private Long performanceId;

        private String exchangeCode;

        private Long userId;
    }

    }
