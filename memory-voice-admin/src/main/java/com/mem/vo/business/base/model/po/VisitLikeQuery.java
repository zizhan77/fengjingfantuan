package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class VisitLikeQuery {
    private Integer id;

    /**
     * 访谈id
     */
    private Integer visitId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 创建用户
     */
    private String createUser;


    /**
     * 删除标识  0 有效 1 无效
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
