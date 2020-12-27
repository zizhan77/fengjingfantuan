package com.mem.vo.business.biz.service.impl.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mem.vo.business.biz.model.vo.sms.SmsResponseVO;
import com.mem.vo.business.biz.service.sms.SmsService;
import com.mem.vo.common.constant.RedisConstant;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SmsEnum;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.RedisUtils;
import com.mem.vo.config.AliyuncsConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 17:02
 */
@Service("smsService")
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Resource
    private AliyuncsConfig aliyuncsConfig;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public Boolean sendSms(String phoneNumber) {
        DefaultProfile profile = DefaultProfile.getProfile(aliyuncsConfig.getRegionId(), aliyuncsConfig.getAccessKeyId(), aliyuncsConfig.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(aliyuncsConfig.getDomain());
        request.setVersion(aliyuncsConfig.getVersion());
        request.setAction(aliyuncsConfig.getAction());
        request.putQueryParameter(SmsEnum.PHONE_NUMBERS.getCode(), phoneNumber);
        request.putQueryParameter(SmsEnum.TEMPLATE_CODE.getCode(), aliyuncsConfig.getTemplateCode());
        request.putQueryParameter(SmsEnum.SIGN_NAME.getCode(), aliyuncsConfig.getSignName());
        //生成随机数
        Random ne = new Random();
        int x = ne.nextInt(9999 - 1000 + 1) + 1000;
        //入参必须是<String, String>格式
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(SmsEnum.FIELD_CODE.getCode(), String.valueOf(x));
        request.putQueryParameter(SmsEnum.TEMPLATE_PARAM.getCode(), JsonUtil.toJson(queryParameters));

        String redisKey = RedisPrefix.SMS_REDIS_PREFIX.getCode() + phoneNumber;
        try {
            //添加redis纪录
            if (!redisUtils.setnx(redisKey, String.valueOf(x), RedisConstant.EXPIRED_TIME_5M)) {
                throw new BizException("您的验证码发送太频繁！，请稍后再试");
            }
            CommonResponse response = client.getCommonResponse(request);
            SmsResponseVO responseVO = JsonUtil.fromJson(response.getData(), SmsResponseVO.class);
            if (responseVO.getCode().equalsIgnoreCase(SmsEnum.SUCCESS_CODE.getCode())) {
                return true;
            } else {
                //发送失败，删除redisKey
                redisUtils.del(redisKey);
                log.error("短信发送异常，异常信息：" + response.getData());
                return false;
            }
        } catch (ClientException e) {
            log.error("发送短信获取验证码异常！", e);
            throw new BizException("发送短信获取验证码异常！");
        }
    }

    @Override
    public Boolean checkSms(String phoneNumber, String verificationCode) {
        String redisKey = RedisPrefix.SMS_REDIS_PREFIX.getCode() + StringUtils.trim(phoneNumber);
        String value = redisUtils.get(redisKey);
        if (value == null) {
            log.error("redis中没有{}的验证码,请重新获取验证码，并检查redis是否故障！",phoneNumber);
            throw new BizException("验证码过期，请重新获取验证码！");
        }
        return value.equals(verificationCode);
    }
}
