package com.mem.vo.business.base.model.po;import lombok.Data;import java.util.Date;/** * * <br> * <b>功能：</b>BasicAddress实体类<br> */@Datapublic class BasicAddress {	/**	 *	 */	private Long id;	/**	 *	 */	private String addressCode;	/**	 *	 */	private String addressName;	/**	 *	 */	private String parentCode;	/**	 * 1省份；2城市；3区县；4街道	 */	private Integer level;	/**	 *	 */	private Integer status;	/**	 *	 */	private Date createTime;	/**	 *	 */	private String createUser;	/**	 *	 */	private Date updateTime;	/**	 *	 */	private String updateUser;	/**	 *	 */	private Integer isDelete;	/**	 *	 */	private Date ts;}