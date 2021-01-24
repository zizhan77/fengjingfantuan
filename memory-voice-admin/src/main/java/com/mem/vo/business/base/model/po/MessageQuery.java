package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;
@Data
public class MessageQuery {

    private Integer id;

    private Integer sponsorId;

    private String articleId;

    private Integer organizerId;

    private String status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private int isDelete;

    private Integer total;
    private Integer limit;
    private Integer page;


}
