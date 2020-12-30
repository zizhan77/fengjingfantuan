package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Ranking {
    private Long id;

    private String name;

    private Date startTime;

    private Date endTime;

    private String count;

    private String url;

    private String isDelete;

    private String enable;

    private Date creatTime;

    private String stringstartTime;

    private String stringEndTime;

    private String thumbUrl;

    private List<User> users;

    private Integer sort;

    private String provence;

    private String intro;

    private Integer isusertop;
}
