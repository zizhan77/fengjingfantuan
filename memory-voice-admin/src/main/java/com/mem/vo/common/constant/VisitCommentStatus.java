package com.mem.vo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangsq
 * @date 2021-03-28 06：28
 * @desc
 */
@AllArgsConstructor
@Getter
public enum VisitCommentStatus {

    COMMENTWAIT(0, "评论审核中"),
    COMMENTPASS(1, "评论审核通过"),
    COMMNETFORBID(2, "评论审核未通过");
    private Integer code;
    private String name;
}
