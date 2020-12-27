package com.mem.vo.business.base.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVO {
    private long id;

    private String articleName;

    private String showTime;

    private String artistName;

    private String addressName;

    private String placeName;

    private String price;

    private String stopTime;

    private String status;

    private String className;

    private String organizerName;

    private Integer artistId;

    private Integer placeId;

    private Integer cityId;


}
