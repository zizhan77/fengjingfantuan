package com.mem.vo.business.base.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVOBuilder {
    private Long id;

    private String phoneNumber;

    private String bizCode;

    private String name;

    private Integer gender;

    private Date birthday;

    private Integer integral;

    private Integer integralflag;
}
