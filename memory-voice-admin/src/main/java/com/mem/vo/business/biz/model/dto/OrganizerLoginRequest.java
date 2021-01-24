package com.mem.vo.business.biz.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerLoginRequest {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名为空")
    String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码为空")
    String password;
}
