package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;
/**
 *
 * <br>
 * <b>功能：</b>ActivityUser实体类<br>
 */
@Data
public class ActivityUser{


    /**
     *
     */
    private Long id;
    /**
     * 关联用户ID
     */
    private String userId;
    /**
     * 关联用户名
     */
    private String userName;
    /**
     * 关联活动ID
     */
    private Integer activityId;
    /**
     * 关联活动名称
     */
    private String activityName;
    /**
     * 通关次数
     */
    private Integer passQty;

    /**
     * 抽奖次数
     */
    private Integer lotteryQty;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String createUser;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private String updateUser;
    /**
     * 删除标识  0 有效 1 无效
     */
    private Integer isDelete;

    private Integer isShare;
}


