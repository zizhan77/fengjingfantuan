package com.mem.vo.business.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-29 20:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 业务主键
     */
    private String bizCode;
    /**
     * 用户名
     */
    private String name;
    /**
     * 性别 0未添加 男1 女2
     */
    private Integer gender;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 积分
     */
    private Integer integral;
}
