package com.mem.vo.business.biz.model.vo.authority;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 18:28
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorityMenuVo {

    /**
     *
     */
    private Long id;
    /**
     * 菜单编号
     */
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * url
     */
    private String menuUrl;
    /**
     * 排序
     */
    private Integer sortNum;
    /**
     * 备注
     */
    private String comment;

    /**
     * 创建时间
     */
    private Date createTime;

}
