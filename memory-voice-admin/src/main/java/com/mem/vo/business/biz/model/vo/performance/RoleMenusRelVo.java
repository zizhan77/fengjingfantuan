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

    public static void main(String[] args) {
        List<Integer> menuids = new ArrayList<>();
        menuids.add(3);
        menuids.add(5);
        RoleMenusRelVo vo = RoleMenusRelVo.builder().roleId(3).menuIds(menuids).build();
        System.out.println(JsonUtil.toJson(vo));
    }
}
