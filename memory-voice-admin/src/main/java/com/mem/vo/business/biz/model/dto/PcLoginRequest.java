package com.mem.vo.business.biz.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.mem.vo.common.constant.RegexPattern;
import com.mem.vo.common.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 11:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PcLoginRequest {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = RegexPattern.phonenumber, message = "手机号格式错误")
    String phoneNumber;

    /**
     * 密码
     */
    @NotBlank(message = "密码为空")
    //@Pattern(regexp = RegexPattern.passwd, message = "密码格式错误")
    String password;

    public static void main(String[] args) {
        PcLoginRequest pcLoginRequest = new PcLoginRequest("222", "pwpww");
        System.out.println(JsonUtil.toJson(pcLoginRequest));
    }

}
