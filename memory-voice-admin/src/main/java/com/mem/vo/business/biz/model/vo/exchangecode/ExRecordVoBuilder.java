package com.mem.vo.business.biz.model.vo.exchangecode;

import lombok.Data;

@Data
public class ExRecordVoBuilder {
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
