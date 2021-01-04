package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PrizeBuilder {
    private Integer id;

    private String prizeName;

    private Long activityId;

    private BigDecimal prob;

    private Integer totalNum;

    private Integer givedNum;

    private Integer prizeType;

    private String prizeIntro;

    private String prizeRule;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Date ts;

    private Integer sponsorId;

    private Integer status;

    private Integer level;

    private String count;

    private Integer miniType;

    private Integer codeType;

    private Integer dailyTicketLimit;

    private String spurl;
}
