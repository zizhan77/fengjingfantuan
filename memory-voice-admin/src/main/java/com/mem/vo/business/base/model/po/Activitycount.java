package com.mem.vo.business.base.model.po;

import lombok.Data;

@Data
public class Activitycount {
    private Integer id;

    private String activityId;

    private String userId;

    private String date;

    private String count;
}
