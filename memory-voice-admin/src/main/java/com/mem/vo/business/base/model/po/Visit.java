package com.mem.vo.business.base.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {


    private Integer id;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频简介
     */
    private String introduction;

    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 视频封面
     */
    private String thumbnailUrl;

    /**
     * 点赞数
     */
    private long likes;

    /**
     * 评论数
     * */
    private long comments;

    /**
     * 转发数
     * */
    private long forwards;

    /**
     * 删除标识  0 有效 1 无效
     */
    private Integer isDelete;

    /**
     *
     * 启停标注  0 隐藏 1 显示
     * */
    private Integer isShow;

    /**
     * 视频上传用户
     */
    private long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
