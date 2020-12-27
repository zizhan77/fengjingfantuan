package com.mem.vo.business.base.model.vo;

import lombok.Data;


@Data
public class MessageVO {

    private Integer id;//message主键

    private String sponsorName;//赞助商名字

    private String articleName;//项目名字

    private String messageTime;

    private String sponsorPhone;

    private String organizerPhone;

    private String organizerName;

    private String status;

}
