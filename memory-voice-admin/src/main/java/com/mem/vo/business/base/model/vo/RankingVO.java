package com.mem.vo.business.base.model.vo;

import lombok.Data;
import java.util.Date;

@Data
public class RankingVO {
    private Long id;

    private String name;

    private Date startTime;

    private Date endTime;

    private String count;

    private String url;

    private String ranking;

    private String isDelete;

    private String enable;

    private Date creatTime;
}
