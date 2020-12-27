package com.mem.vo.business.biz.model.vo.sms;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 17:40
 */
@Data
public class SmsResponseVO {

    @JSONField(name = "Code")
    private String code;

    @JSONField(name = "RequestId")
    private String requestId;

    @JSONField(name = "BizId")
    private String bizId;

    @JSONField(name = "Message")
    private String message;
}
