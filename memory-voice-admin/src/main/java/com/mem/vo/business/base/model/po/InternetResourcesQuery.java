package com.mem.vo.business.base.model.po;import java.util.Date;import java.util.List;import java.math.BigDecimal;/** *  * <br> * <b>功能：</b>InternetResources实体类<br> */public class InternetResourcesQuery{		/**	* 	*/	private Integer id;	/**	* 	*/	private String name;	/**	* 	*/	private String url;	/**	* 	*/	private Date createTime;	/**	* 	*/	private String createUser;	/**	* 	*/	private Date updateTime;	/**	* 	*/	private String updateUser;	/**	* 删除标识  0 有效 1 无效	*/	private Integer isDelete;	/**	* 赞助商ID，如果关联赞助商的话，会有	*/	private Integer sponsorId;	/**	* 资源类型：0，音频；1，视频	*/	private Integer type;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}	public String getUrl() {	    return this.url;	}	public void setUrl(String url) {	    this.url=url;	}	public Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}	public String getCreateUser() {	    return this.createUser;	}	public void setCreateUser(String createUser) {	    this.createUser=createUser;	}	public Date getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(Date updateTime) {	    this.updateTime=updateTime;	}	public String getUpdateUser() {	    return this.updateUser;	}	public void setUpdateUser(String updateUser) {	    this.updateUser=updateUser;	}	public Integer getIsDelete() {	    return this.isDelete;	}	public void setIsDelete(Integer isDelete) {	    this.isDelete=isDelete;	}	public Integer getSponsorId() {	    return this.sponsorId;	}	public void setSponsorId(Integer sponsorId) {	    this.sponsorId=sponsorId;	}	public Integer getType() {	    return this.type;	}	public void setType(Integer type) {	    this.type=type;	}}