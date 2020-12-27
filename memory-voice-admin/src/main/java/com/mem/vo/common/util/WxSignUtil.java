package com.mem.vo.common.util;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.biz.model.dto.WxJsTokenResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.expression.Lists;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Description: com.mem.vo.common.util
 * User: baiyuehui
 * Date: 2019/6/26
 */
public class WxSignUtil {

    /**
     * sign 签名 （参数名按ASCII码从小到大排序（字典序）+key+MD5+转大写签名）
     * @param map
     * @return
     */
    public static String encodeSign(SortedMap<String,String> map, String key){
        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("签名key不能为空");
        }
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        List<String> values = new ArrayList();

        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = String.valueOf(entry.getKey());
            String v = String.valueOf(entry.getValue());
            if (StringUtils.isNotEmpty(v) && entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {
                values.add(k + "=" + v);
            }
        }
        values.add("key="+ key);
        String sign = StringUtils.join(values, "&");
        return encodeByMD5(sign).toUpperCase();
    }
    public static String initJsSignBySHA1(SortedMap<String,String> map){
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        List<String> values = new ArrayList();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = String.valueOf(entry.getKey());
            String v = String.valueOf(entry.getValue());
            if (StringUtils.isNotEmpty(v) && entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {
                values.add(k + "=" + v);
            }
        }
        String sign = StringUtils.join(values, "&");
        return encodeBySHA1(sign);
    }
    /**
     * 验证微信后台配置的服务器地址有效性
     * <p>
     * 接收并校验四个请求参数
     *
     * @param url       URL
     * @param timestamp 时间戳
     * @param ticket    ticket
     * @param echostr   随机字符串
     * @return echostr
     *//*
    @GetMapping("/getSignature")
    public Map<String, String> checkName(WxJsTokenResponse response, String url) {

        System.out.println("微信-开始校验签名");
        System.out.println("收到来自微信的 echostr 字符串:{echostr}");

        *//* 加密/校验流程如下:
         *1. 将token、timestamp、nonce三个参数进行字典序排序
         * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
         * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信 *//*

        System.out.println("微信-开始排序");
        // 1.排序
        String sortString = sort(url, response.getTimestamp(), response.getTicket(),response.getNoncestr());
        System.out.println("微信-开始sha1加密");
        // 2.sha1加密
        String signature = sha1(sortString);
        // 工具类的加密方法
        // String signature = DigestUtils.sha1Hex(sortString);
        Map map = new HashMap<>();
        map.put("appId", APP_ID);
        map.put("url", url);
        map.put("timestamp", timestamp);
        map.put("ticket", ticket);
        map.put("echostr", echostr);
        map.put("str", sortString);
        map.put("signature", signature );
        return map;
    }

    *//**
     * @Author zhaohp
     * @Date 2018/12/10 19:50
     * @Param [signature, timestamp, ticket, echostr]
     * @Return java.lang.String
     * @Description: 对所有待签名参数按照字段名的ASCII 码从小到大排序
     *//*
    public String sort(String url, String timestamp, String ticket, String echostr) {
        return "jsapi_ticket=" + ticket + "&noncestr=" + echostr + "&timestamp=" + timestamp + "&url=" + url;
    }

    *//**
     * @Author zhaohp
     * @Date 2018/12/10 19:51
     * @Param [str]
     * @Return java.lang.String
     * @Description: 将字符串进行sha1加密
     *//*
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // 创建 16进制字符串
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }*/

    /**
     * 通过MD5加密
     *
     * @param algorithmStr
     * @return String
     */
    public static String encodeByMD5(String algorithmStr) {
        if (algorithmStr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(algorithmStr.getBytes("utf-8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 加密算法 把密文转成16进制的字符串形式
    public static String getFormattedText(byte[] bytes) {
        final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }


    public static String encodeBySHA1(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes("utf-8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
