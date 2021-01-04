package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PrizeDQueryBuilder {
    private Integer id;

    private Integer prizeId;

    private Integer prizeType;

    private Integer integralQty;

    private BigDecimal prob;

    private String eticketCode;

    private String name;

    private Integer isChange;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Date ts;

    private Long activityId;

    private String prizeIntro;

    private String prizeName;

    private BigDecimal integralProb;

    private Integer level;

    private Integer prizedCount;

    private Integer ticketUnit;

    private Integer miniType;

    private Integer codeType;

    private Integer keyId;

    private Integer dailyTicketLimit;
}
