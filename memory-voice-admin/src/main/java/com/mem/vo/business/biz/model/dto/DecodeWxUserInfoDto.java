package com.mem.vo.business.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/1 12:51
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DecodeWxUserInfoDto {

    String encryptedData;
    String iv;
    String userInfo;

}
