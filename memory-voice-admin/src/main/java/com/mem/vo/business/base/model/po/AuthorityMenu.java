package com.mem.vo.business.base.model.po;import lombok.Data;import java.util.Date;/** * <br> * <b>功能：</b>AuthorityMenu实体类<br> */@Datapublic class AuthorityMenu {    /**     *     */    private Long id;    /**     * 菜单编号     */    private Integer menuId;    /**     * 菜单名称     */    private String menuName;    /**     * url     */    private String menuUrl;    /**     * 排序     */    private Integer sortNum;    /**     * 备注     */    private String comment;    /**     *     */    private Date createTime;    /**     *     */    private String createUser;    /**     *     */    private Date updateTime;    /**     *     */    private String updateUser;    /**     *     */    private Integer isDelete;    /**     *     */    private Date ts;}