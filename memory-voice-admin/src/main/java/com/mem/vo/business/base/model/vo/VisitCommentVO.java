package com.mem.vo.business.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitCommentVO {

    private Integer id;

    /**
     * 访谈id
     */
    private long visitId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatarurl;

    /**
     * 创建用户
     */
    private long createUser;

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
     * 评论回复列表
     */
    private List<VisitReplyCommentVO> visitReplyCommentVOs;

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
