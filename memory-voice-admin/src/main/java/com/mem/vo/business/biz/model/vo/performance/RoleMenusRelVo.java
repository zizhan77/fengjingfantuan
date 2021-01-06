package com.mem.vo.business.biz.model.vo.performance;

import java.util.ArrayList;
import java.util.List;

import com.mem.vo.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 15:02
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenusRelVo {

    /**
     * 角色编码
     */
    private Integer roleId;

    /**
     * 菜单
     */
    private List<Integer> menuIds;
}
