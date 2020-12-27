package com.mem.vo.business.biz.model.dto;

import lombok.Data;

/**
 * @author baiyuehui
 * @description: 微信支付二次签名
 * @date 2019/5/25 10:13
 */
@Data
public class WxOrderResign {

    /**
     * 小程序 appId
     */
    private String appid;
    /**
     * 时间戳
     */
    private String timeStamp = System.currentTimeMillis()+"";
    /**
     * 随机字符串
     */
    private String nonce_str = System.currentTimeMillis()+"1";
    /**
     * 数据包,统一下单接口返回的 prepay_id 参数值
     */
    private String a_package ;

    //签名方式
    private String signType="MD5";


    //计算出的值
    private String sign;


}
