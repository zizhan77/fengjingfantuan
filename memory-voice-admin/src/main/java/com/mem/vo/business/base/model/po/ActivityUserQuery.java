package com.mem.vo.business.base.model.po;

import java.util.Date;
/**
 *
 * <br>
 * <b>功能：</b>ActivityUser实体类<br>
 */
public class ActivityUserQuery{


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

    public Integer getLotteryQty() {
        return lotteryQty;
    }

    public void setLotteryQty(Integer lotteryQty) {
        this.lotteryQty = lotteryQty;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id=id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId=userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName=userName;
    }
    public Integer getActivityId() {
        return this.activityId;
    }
    public void setActivityId(Integer activityId) {
        this.activityId=activityId;
    }
    public String getActivityName() {
        return this.activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName=activityName;
    }
    public Integer getPassQty() {
        return this.passQty;
    }
    public void setPassQty(Integer passQty) {
        this.passQty=passQty;
    }
    public Date getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime=createTime;
    }
    public String getCreateUser() {
        return this.createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser=createUser;
    }
    public Date getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime=updateTime;
    }
    public String getUpdateUser() {
        return this.updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser=updateUser;
    }
    public Integer getIsDelete() {
        return this.isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete=isDelete;
    }
}


