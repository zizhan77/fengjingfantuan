package com.mem.vo.business.base.model.po;import lombok.Data;import java.util.Date;/** * <br> * <b>功能：</b>AuthorityRole实体类<br> */@Datapublic class AuthorityRole {    /**     *     */    private Long id;    /**     * 角色编码     */    private Integer roleId;    /**     * 角色名称     */    private String name;    /**     * 状态 0 启用 1 停用     */    private Integer status;    /**     *     */    private Date createTime;    /**     *     */    private String createUser;    /**     *     */    private Date updateTime;    /**     *     */    private String updateUser;    /**     *     */    private Integer isDelete;    /**     *     */    private Date ts;}