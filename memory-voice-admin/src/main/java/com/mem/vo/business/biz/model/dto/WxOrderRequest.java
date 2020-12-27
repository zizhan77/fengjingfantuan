package com.mem.vo.business.biz.model.dto;

import com.mem.vo.common.util.XStreamCDATA;
import lombok.Data;

/**
 * @author baiyuehui
 * @description: 微信登录验证请求参数
 * @date 2019/5/25 10:13
 */
@Data
public class WxOrderRequest {


    @XStreamCDATA
    private String shopKey;
    /**
     * 小程序 appId
     */

    @XStreamCDATA
    private String appid;
    /**
     * 商户号
     */

    @XStreamCDATA
    private String mch_id;
    /**
     * 设备号
     */
    @XStreamCDATA
    private String device_info = "WEB";
    /**
     * 随机字符串
     */

    //System.currentTimeMillis()+"1"
    @XStreamCDATA
    private String nonce_str = "5d1cae2945e97";

    //计算出的值

    @XStreamCDATA
    private String sign;

    //商品描述 记忆之声-演出购票

    @XStreamCDATA
    private String body = "记忆之声-购票";

    //商户订单号

    @XStreamCDATA
    private String out_trade_no;

    //标价金额

    @XStreamCDATA
    private Integer total_fee;

    //终端IP

    @XStreamCDATA
    private String spbill_create_ip;

    //交易起始时间

    @XStreamCDATA
    private String time_start;

    //通知地址
    @XStreamCDATA
    private String notify_url;

    //交易类型
    @XStreamCDATA
    private String trade_type="JSAPI";

    //openId
    @XStreamCDATA
    private String openid;

    //以下为确认支付，二次签名使用
    //appId

    @XStreamCDATA
    private String timeStamp = System.currentTimeMillis()+"";

    //package 统一下单接口返回的 prepay_id 参数值

    @XStreamCDATA
    private String a_package ="";


    @XStreamCDATA
    private String sign_type ="MD5";



}
