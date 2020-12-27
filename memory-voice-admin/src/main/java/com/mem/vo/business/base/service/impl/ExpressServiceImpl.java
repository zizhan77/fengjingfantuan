package com.mem.vo.business.base.service.impl;

import com.mem.vo.business.base.model.po.Express;
import com.mem.vo.business.base.model.po.ExpressReturn;
import com.mem.vo.business.base.service.ExpressService;
import com.mem.vo.common.constant.RedisConstant;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.HttpUtils;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-08-09 21:22
 */
@Service("expressService")
public class ExpressServiceImpl implements ExpressService {

    @Value("${aliyuncs.expressAppCode}")
    private String appCode;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public Express getExpressInfo(String waybillCode) {
        String res = redisUtils.get(RedisPrefix.EXPRESS + waybillCode);
        if (StringUtils.isNotBlank(res)) {
            return JsonUtil.fromJson(res, Express.class);
        }
        String host = "http://wuliu.market.alicloudapi.com";
        String path = "/kdi";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appCode); //格式为:Authorization:APPCODE 83359fd73fe11248385f570e3c139xxx
        Map<String, String> querys = new HashMap<>();
        querys.put("no", waybillCode);// !!! 请求参数
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            res = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
        if (StringUtils.isBlank(res)) {
            return new Express();
        }
        ExpressReturn expressReturn = JsonUtil.fromJson(res, ExpressReturn.class);
        if (expressReturn.getStatus().equals("0")) {
            redisUtils.setex(RedisPrefix.EXPRESS + waybillCode, expressReturn.getResult().toString(), RedisConstant.EXPIRED_TIME_1H);
            return expressReturn.getResult();
        }
        throw new BizException("获取快递信息异常，异常信息:" + res);
    }
}
