package com.mem.vo.business.base.model.po;

import lombok.Data;

@Data
public class ArticlePo {
    private Integer id;
    private Integer sponsorid;
    private String content;
    private String showTime;
    private Integer artistid;
    private Integer cityid;
    private String placeid;
    private String placeName;
    private String price;
    private String showTime2;
    private Integer organizerid;
    private String classid;
    private String stopTime;
    private String status;
    private String isDelete;
}
