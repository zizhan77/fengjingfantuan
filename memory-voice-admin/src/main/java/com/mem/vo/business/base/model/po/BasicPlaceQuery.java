package com.mem.vo.business.base.model.po;import lombok.Data;import java.math.BigDecimal;import java.util.Date;/** * * <br> * <b>功能：</b>BasicPlace实体类<br> */@Datapublic class BasicPlaceQuery{	/**	* 主键	*/	private Long id;	/**	* 场地名称	*/	private String name;	private String phone;	/**	*	*/	private String oneAddress;	/**	*	*/	private String twoAddress;	/**	*	*/	private String threeAddress;	/**	*	*/	private String address;	/**	*	*/	private Integer chooseSeat;	/**	*	*/	private Integer enable;	/**	*	*/	private BigDecimal longitude;	/**	*	*/	private BigDecimal latitude;	/**	*	*/	private Date createTime;	/**	*	*/	private String createUser;	/**	*	*/	private Date updateTime;	/**	*	*/	private Date updateUser;	/**	*	*/	private Integer isDelete = 0;	/**	*	*/	private Date ts;	private BigDecimal lowPrice;	private BigDecimal topPrice;	private String topCount;	private String lowCount;	private String introduce;	private Integer artistId;}