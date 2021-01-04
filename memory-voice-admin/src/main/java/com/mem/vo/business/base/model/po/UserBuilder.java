package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserBuilder {
    private Long id;

    private String phoneNumber;

    private String bizCode;

    private String name;

    private String password;

    private String source;

    private String sourceName;

    private Integer status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Date ts;

    private Integer role;

    private Integer form;

    private Integer isAuthorize;

    private Integer gender;

    private Date birthday;

    private Integer integral;

    private Integer rankintegral;

    private Integer integralflag;

    private String avatarurl;

    private Integer sort;

    private Integer whocount;

    private Integer actcount;

    private Integer address;

    private String time;

    private Integer isaddress;

    private Integer overdata;
}
