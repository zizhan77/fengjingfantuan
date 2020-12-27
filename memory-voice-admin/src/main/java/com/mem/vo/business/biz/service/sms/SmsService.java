package com.mem.vo.business.biz.service.sms;


/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 16:58
 */
public interface SmsService {

    /**
     * 发送短信接口
     * @param phoneNumber 电话号码
     * @return  返回是否成功
     */
    Boolean sendSms(String phoneNumber);

    /**
     * 验证码验证
     * @param phoneNumber 电话号
     * @param verificationCode   验证码
     * @return 返回是否成功
     */
    Boolean checkSms(String phoneNumber, String verificationCode);
}
