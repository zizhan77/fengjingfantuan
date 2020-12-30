package com.mem.vo.business.base.model.vo;

import lombok.Data;

@Data
public class RewardQueryDto {
    private Long contactPhone;

    private String startsedate;

    private String endsedate;

    private String activityName;

    private String prizeName;

    private Integer page;

    private Integer prizeType;

    private Integer pageSize;
}
