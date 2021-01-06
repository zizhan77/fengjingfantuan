package com.mem.vo.business.biz.model.vo.exchangecode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description
 * @date 2019/7/7 22:13
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExRecordVo {
    /**
     * //业务标识
     */
    private Integer businessTag;

    /*
     * 兑换码
     */
    private String exchangeCode;

    /**
     *  演出id
     *
     */

    private Long performanceId;

    /**
     * 演出名称
     */
    private String performanceName;

    /**
     * 场次id
     */
    private Long showId;

    /**
     * 场次名称
     */
    private String showName;

    /**
     * 区域id
     */
    private Long areaId;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 票档id
     */
    private Long ticketGearId;


    /**
     * 票档名称
     */
    private String ticketGearName;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statuaName;

    public static ExRecordVoBuilder builder() {
        return new ExRecordVoBuilder();
    }

    public static class ExRecordVoBuilder {
        private Integer businessTag;

        private String exchangeCode;

        private Long performanceId;

        private String performanceName;

        private Long showId;

        private String showName;

        private Long areaId;

        private String areaName;

        private Long ticketGearId;

        private String ticketGearName;

        private String status;

        private String statuaName;
    }

}
