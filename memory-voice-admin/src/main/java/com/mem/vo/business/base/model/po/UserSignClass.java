package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserSignClass {
    private Integer id;

    private String userId;

    private String userName;

    private String targetId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Integer type;

    private Integer shareFrom;

}
