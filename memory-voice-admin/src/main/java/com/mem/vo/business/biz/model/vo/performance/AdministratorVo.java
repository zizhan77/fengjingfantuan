package com.mem.vo.business.biz.model.vo.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 14:40
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorVo {

    /**
     * 主键
     */
    private Long id;

    private String accountNumber;

    private String password;

    /**
     * 角色编码
     */
    private Integer role;

    private String roleName;

    private Integer status;

}
