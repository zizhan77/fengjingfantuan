package com.mem.vo.business.base.model.po;import java.util.Date;/** * <br> * <b>功能：</b>Seats实体类<br> */public class SeatsQuery{    /**     * 主键 演出id     */    private Long id;    /**     * 行号     */    private String lineNo;    /**     * 场地号     */    private Long placeId;    /**     * 演出id     */    private Long performanceId;    /**     * 区域id     */    private Long areaId;    /**     * 座位状态 // 0：间隔；1：已开启的座位；2：已关闭的座位；3：已售出的座位     */    private Integer status;    /**     *     */    private Date createTime;    /**     *     */    private String createUser;    /**     *     */    private Date updateTime;    /**     *     */    private String updateUser;    /**     * 删除标识  0 有效 1 无效     */    private Integer isDelete;    /**     *     */    private Date ts;    public Long getId() {        return this.id;    }    public void setId(Long id) {        this.id=id;    }    public String getLineNo() {        return this.lineNo;    }    public void setLineNo(String lineNo) {        this.lineNo=lineNo;    }    public Long getPlaceId() {        return this.placeId;    }    public void setPlaceId(Long placeId) {        this.placeId=placeId;    }    public Long getPerformanceId() {        return this.performanceId;    }    public void setPerformanceId(Long performanceId) {        this.performanceId=performanceId;    }    public Long getAreaId() {        return this.areaId;    }    public void setAreaId(Long areaId) {        this.areaId=areaId;    }    public Integer getStatus() {        return this.status;    }    public void setStatus(Integer status) {        this.status=status;    }    public Date getCreateTime() {        return this.createTime;    }    public void setCreateTime(Date createTime) {        this.createTime=createTime;    }    public String getCreateUser() {        return this.createUser;    }    public void setCreateUser(String createUser) {        this.createUser=createUser;    }    public Date getUpdateTime() {        return this.updateTime;    }    public void setUpdateTime(Date updateTime) {        this.updateTime=updateTime;    }    public String getUpdateUser() {        return this.updateUser;    }    public void setUpdateUser(String updateUser) {        this.updateUser=updateUser;    }    public Integer getIsDelete() {        return this.isDelete;    }    public void setIsDelete(Integer isDelete) {        this.isDelete=isDelete;    }    public Date getTs() {        return this.ts;    }    public void setTs(Date ts) {        this.ts=ts;    }}