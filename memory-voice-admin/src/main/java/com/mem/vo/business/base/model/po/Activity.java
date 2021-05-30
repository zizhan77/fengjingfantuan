package com.mem.vo.business.base.model.po;import lombok.AllArgsConstructor;import lombok.Data;import lombok.NoArgsConstructor;import java.util.Date;/** * * <br> * <b>功能：</b>Activity实体类<br> */@Data@NoArgsConstructor@AllArgsConstructorpublic class Activity{    private Long id;    private String activityTitle;    private String activityIntro;    private Integer type;    private String startDate;    private String endDate;    private Integer status;    private String rankstart;    private String rankend;    private Integer rankstatus;    private Integer sort;    private String activityUrl;    private Date createTime;    private String createUser;    private Date updateTime;    private String updateUser;    private Integer isDelete;    private Date ts;    private String sponsorId;    private String sponsorName;    private Integer isOnly;    private String thumbnailUrl;    private String count;    private Integer performanceId;    private String bgUrl;    private String totalNum;    private String givedNum;    private String showNum;    private Integer topsp;    private String crossimage;    private Integer number;    private Long ofranking;    private String provence;    private Integer peoplenum;    private Integer numclick;    private Integer rankprople;    private Integer ranknum;    //    /**//     *//     *///    private Long id;//    /**//     * 活动标题//     *///    private String activityTitle;//    /**//     * 活动简介//     *///    private String activityIntro;//    /**//     * 互动类型//     *///    private Integer type;//    /**//     * 开始时间//     *///    private Date startDate;//    /**//     * 结束时间//     *///    private Date endDate;//    /**//     * status 0不启用，1启用//     *///    private Integer status;//    /**//     * 排序//     *///    private Integer sort;//    /**//     * 活动链接//     *///    private String activityUrl;//    /**//     *//     *///    private Date createTime;//    /**//     *//     *///    private String createUser;//    /**//     *//     *///    private Date updateTime;//    /**//     *//     *///    private String updateUser;//    /**//     * 删除标识  0 有效 1 无效//     *///    private Integer isDelete;//    /**//     *//     *///    private Date ts;//    /**//     * 赞助商id//     *///    private Integer sponsorId;//    /**//     * 赞助商名称//     *///    private String sponsorName;//    /**//     * 是否独家//     *///    private Integer isOnly;//    /**//     * 缩略图url//     *///    private String thumbnailUrl;}