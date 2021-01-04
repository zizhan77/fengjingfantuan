package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class IntegralBuilder {
    private Integer id;

    private Integer userId;

    private Integer integralQty;

    private Integer type;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Integer activityId;

    private String activityName;
}
