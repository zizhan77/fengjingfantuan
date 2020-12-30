package com.mem.vo.business.base.model.po;

import lombok.Data;

/**
 * @author zhangsq
 */
@Data
public class ActivityUrl {
    private Long id;

    private String sponsorId;

    private String url;

    private String status;

    private String isDelete;

    private String sponsorName;

    private Integer enable;
}
