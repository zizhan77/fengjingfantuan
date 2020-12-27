package com.mem.vo.business.biz.model.dto;

import lombok.Data;

/**
 * @author baiyuehui
 * @description: 微信支付首次签名
 * @date 2019/5/25 10:13
 */
@Data
public class WxOrderSign {

    /**
     * 小程序 appId
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 设备号
     */
    private String device_info = "WEB";

    //商品描述
    private String body;
    /**
     * 随机字符串
     */
    private String nonce_str = System.currentTimeMillis()+"1";

    //计算出的值
    private String sign;

    public String toXML(){
        StringBuilder sb = new StringBuilder();
        sb.append(" <xml>");
        sb.append("<appid>");
        sb.append(this.appid);
        sb.append("</appid>");
        sb.append("<mch_id>");
        sb.append(this.mch_id);
        sb.append("</mch_id>");
        sb.append("<device_info>");
        sb.append(this.device_info);
        sb.append("</device_info>");
        sb.append("<body>");
        sb.append(this.body);
        sb.append("</body>");
        sb.append("<nonce_str>");
        sb.append(this.nonce_str);
        sb.append("</nonce_str>");
        sb.append("<sign>");
        sb.append(this.sign);
        sb.append("</sign>");
        sb.append("</xml>");
        return sb.toString();
    }

}
