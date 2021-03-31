package com.mem.vo.business.base.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitComment {

    private Integer id;

    /**
     * 访谈id
     */
    private long visitId;

    /**
     * 主评论id
     */
    private long visitCommentId;

    /**
     * 被评论用户id
     */
    private long replyCreateUser;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 创建用户
     */
    private long createUser;

    /**
     * 用户头像
     */
    private String avatarurl;

    /**
     * 评论内容，限定140个字符。
     */
    private String comments;

    /**
     * 评论图片,多个图片，逗号分隔，最多9张。
     */
    private String thumbnailUrls;

    /**
     * 评论点赞数
     */
    private Integer likes;

    /**
     * 删除标识  0 有效 1 无效
     */
    private Integer isDelete;

    /**
     * 评论审核 0 待审核 1 审核通过 2 审核未通过
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
