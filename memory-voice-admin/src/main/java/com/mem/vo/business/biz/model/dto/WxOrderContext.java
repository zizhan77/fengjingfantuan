package com.mem.vo.business.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/auth.code2Session.html
 *
 * @author litongwei
 * @description: 微信登录验证返回的实体
 * @date 2019/5/25 10:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxOrderContext {

    //返回状态码
    private String return_code;

    //返回信息
    private String return_msg;

    //以下字段在return_code为SUCCESS的时候有返回

    //小程序ID
    private String appid;

    //商户号
    private String mch_id;

    //设备号
    private String device_info;

    //随机字符串
    private String nonce_str;

    //签名
    private String sign;

    //业务结果
    private String result_code;

    //错误代码
    private String err_code;

    //错误代码描述
    private String err_code_des;

    //以下字段在return_code 和result_code都为SUCCESS的时候有返回

    //交易类型
    private String trade_type;

    //预支付交易会话标识
    private String prepay_id;

    //二维码链接
    private String code_url;

    //获取支付结果特定字段 以下字段在return_code为SUCCESS的时候有返回
//    //小程序ID
//    private String appid;
//    //商户号
//    private String mch_id;
//    //设备号
//    private String device_info;
//    //随机字符串
//    private String nonce_str;
//    //签名
//    private String sign;
//    //业务结果
//    private String result_code;
    //用户标识
    private String openid;

    //是否关注公众账号
    private String is_subscribe;
    //微信支付订单号
    private String transaction_id;
    //订单金额
    private String total_fee;
    //现金支付金额
    private String cash_fee;
    //商户订单号
    private String out_trade_no;
    //支付完成时间
    private String time_end;
}
