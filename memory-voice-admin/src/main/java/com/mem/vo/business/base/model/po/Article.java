package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class Article implements Serializable {
    private Integer id;

    private Integer sponsorid;

    private String content;

    private String showTime;

    private Integer artistid;

    private Integer cityid;

    private Integer placeid;

    private String price;

    private Integer organizerid;

    private String classid;

    private String stopTime;

    private String status;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String isDelete;

    private static final long serialVersionUID = 1L;

}