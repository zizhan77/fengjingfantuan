package com.mem.vo.business.base.model.vo;

import lombok.Data;
import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.Sponsor;
import java.util.Date;
import java.util.List;

/**
 * @author zhangsq
 */
@Data
public class ActivityVO {
    private Long id;

    private String bgUrl;

    private String activityTitle;

    private String activityIntro;

    private Integer type;

    private String startDate;

    private String endDate;

    private Integer status;

    private Integer sort;

    private String activityUrl;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isDelete;

    private Date ts;

    private List<Sponsor> sponsors;

    private String sponsorId;

    private String sponsorName;

    private Integer isOnly;

    private String thumbnailUrl;

    private String count;

    private Integer performanceId;

    private Performance performance;

    private String totalNum;

    private String givedNum;

    private Integer topsp;

    private String crossimage;

    private Long ofranking;

    private Integer spid;

    private String spname;

    private String spurl;

    private String spthumburl;

    private String spthumbtype;

    private Integer totalnum;

    private Integer givednum;

    private String provence;

    private String rankstart;

    private String rankend;

    private Integer rankstatus;
}
