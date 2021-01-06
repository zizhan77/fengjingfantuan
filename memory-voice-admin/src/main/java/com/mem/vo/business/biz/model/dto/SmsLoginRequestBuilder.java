package com.mem.vo.business.biz.model.dto;

import lombok.Data;

@Data
public class SmsLoginRequestBuilder {
    private String phoneNumber;

    private String verificationCode;

    private String sourceName;
}
