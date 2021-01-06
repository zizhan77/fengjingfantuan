package com.mem.vo.business.biz.model.dto;

import lombok.Data;

@Data
public class WxOrderContextBuilder {
    private String return_code;

    private String return_msg;

    private String appid;

    private String mch_id;

    private String device_info;

    private String nonce_str;

    private String sign;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String trade_type;

    private String prepay_id;

    private String code_url;

    private String openid;

    private String is_subscribe;

    private String transaction_id;

    private String total_fee;

    private String cash_fee;

    private String out_trade_no;

    private String time_end;
}
