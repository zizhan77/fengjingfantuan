package com.mem.vo.business.biz.model.dto;

import lombok.Data;

@Data
public class DecodeWxUserInfoDtoBuilder {
    private String encryptedData;

    private String iv;

    private String userInfo;
}
