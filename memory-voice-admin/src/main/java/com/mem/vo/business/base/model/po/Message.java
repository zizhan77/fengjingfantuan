package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Message implements Serializable {
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

    private static final long serialVersionUID = 1L;


}