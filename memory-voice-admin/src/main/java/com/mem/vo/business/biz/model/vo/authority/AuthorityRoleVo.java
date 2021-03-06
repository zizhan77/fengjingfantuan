package com.mem.vo.business.biz.model.vo.authority;

import java.util.Date;
import java.util.List;

import com.mem.vo.business.base.model.po.AuthorityMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 18:19
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorityRoleVo {

    /**
     *
     */
    private Long id;
    /**
     * 角色编码
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 状态 0 启用 1 停用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 对应的菜单列表
     */
    private List<AuthorityMenu> authorityMenuList;


}
