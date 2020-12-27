package com.mem.vo.business.biz.service.impl.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.mem.vo.MemVoAdminApplication;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.dto.WxJsTokenResponse;
import com.mem.vo.business.biz.model.dto.WxLoginRequest;
import com.mem.vo.business.biz.model.dto.WxRpcContext;
import com.mem.vo.business.biz.service.login.WxLoginService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.LoginStatus;
import com.mem.vo.common.constant.RedisPrefix;
import com.mem.vo.common.constant.SourceType;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.HttpClientUtils;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.RedisUtils;
import com.mem.vo.common.util.WxSignUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/25 10:38
 */
@Service
@Slf4j
public class WxLoginServiceImpl implements WxLoginService {

    @Resource
    private TokenService tokenService;

    @Resource
    private WxLoginRequest wxLoginRequest;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public WxRpcContext getRpcContext(String jsCode) {

        String apiUrl = MessageFormat.format("https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code"
                + "={2}&grant_type={3}", wxLoginRequest.getAppid(), wxLoginRequest.getAppSecret(),
        jsCode, wxLoginRequest.getGrantType());

        String resJson = HttpClientUtils.get(apiUrl);
        log.info("调用wx小程序接口获取appid 的结果为：{}",resJson);
        BizAssert.hasText(resJson, BizCode.BIZ_1002.getMessage());
        WxRpcContext wxRpcContext;
        try {
            wxRpcContext = JsonUtil.fromJson(resJson, WxRpcContext.class);
            BizAssert.notNull(wxRpcContext, BizCode.BIZ_1003.getMessage());
        } catch (Exception e) {
            log.error("{},当前值：{}", BizCode.BIZ_1003.getMessage(), resJson, e);
            throw new BizException(BizCode.BIZ_1003.getMessage());
        }
        return wxRpcContext;
    }

    @Override
    public String getToken(WxRpcContext wxRpcContext,boolean isAuthorize,Long userId) {

        //查询用户表中 该用户是否已经授权

        CommonLoginContext commonLoginContext = CommonLoginContext.
            builder().sourceCode(SourceType.WX_MINI.getCode()).
            bizCode(wxRpcContext.getOpenid()).sessionKey(wxRpcContext.getSession_key())
            .userId(userId)
            .build();

        if(isAuthorize){
            commonLoginContext.setStatus(LoginStatus.SUCCESSFUL.getCode());
        }else{
            commonLoginContext.setStatus(LoginStatus.LOGIN_NOT_AUTH.getCode());
        }
        return tokenService.getTokenByContext(commonLoginContext, RedisPrefix.TOKEN.getCode());
    }

    @Override
    public WxJsTokenResponse getJsAccessToken() {

        WxJsTokenResponse response = new WxJsTokenResponse();

        String token = redisUtils.get("access_token");
        if(token!=null){
            log.error("redis Access_token:"+token);
            response.setAccess_token(token);
            response.setAppId(wxLoginRequest.getJsAppid());
            return response;
        }else{
            String apiUrl =
                    MessageFormat.format("https://api.weixin.qq.com/cgi-bin/token?appid={0}&secret={1}&grant_type={2}",
                            wxLoginRequest.getJsAppid(), wxLoginRequest.getJsAppSecret(), "client_credential");
            String resJson = HttpClientUtils.get(apiUrl);
            log.info("调用wx接口获取AccessToken 的结果为：{}",resJson);
            BizAssert.hasText(resJson, BizCode.FIELD_CHECK_ERROR.getMessage());
            response = JsonUtil.fromJson(resJson, WxJsTokenResponse.class);
            if(response.getAccess_token() != null){
                redisUtils.setnx("access_token",response.getAccess_token(),response.getExpires_in());
                response.setAppId(wxLoginRequest.getJsAppid());
            }else{
                throw new BizException(BizCode.FIELD_CHECK_ERROR.getMessage());
            }
            return response;
        }

    }

    @Override
    public WxJsTokenResponse getWxSign(String url) {
        WxJsTokenResponse response = getJsAccessToken();
        BizAssert.notNull(response,"获取accessToken 为空");
        log.info(response.toString());
        BizAssert.hasText(response.getAccess_token(),"获取 accessToken 值为空串");
        return getJsapiTicket(response,url);
    }

    public WxJsTokenResponse getJsapiTicket(WxJsTokenResponse response,String url){
        String ticket = redisUtils.get("jsapi_ticket");
        SortedMap<String,String> signMap = new TreeMap<>();
        if(ticket!=null){
            response.setTicket(ticket);
        }else{
            String apiUrl = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi",response.getAccess_token());
            String resJson = HttpClientUtils.get(apiUrl);
            log.info("调用wx接口获取jsapi_ticket 的结果为：{}",resJson);
            BizAssert.hasText(resJson, BizCode.FIELD_CHECK_ERROR.getMessage());
            response = JsonUtil.fromJson(resJson, WxJsTokenResponse.class);
            if(response.getErrcode().equals("0")){
                redisUtils.setnx("jsapi_ticket",response.getTicket(),response.getExpires_in());
                response.setTicket(ticket);
            }else{
                throw new BizException(BizCode.FIELD_CHECK_ERROR.getMessage());
            }
        }
        signMap.put("noncestr",response.getNoncestr());
        signMap.put("jsapi_ticket",response.getTicket());
        String javaTimeStamp = System.currentTimeMillis()+"";
        response.setTimestamp(Integer.valueOf(javaTimeStamp.substring(0,javaTimeStamp.length()-3))+"");
        signMap.put("timestamp",response.getTimestamp());
        signMap.put("url",url);
        response.setSignature(WxSignUtil.initJsSignBySHA1(signMap));
        return response;
    }
    public static void main(String[] args) {
        SortedMap<String,String> signMap = new TreeMap<>();
        signMap.put("noncestr","15625099529441");
        signMap.put("jsapi_ticket","LIKLckvwlJT9cWIhEQTwfOMYUupxbfrKpWMF4tIEzJV2T330rIw1JGUrc1iBIw0biW1MFgn7c8hTYYOWx65JVQ");
        signMap.put("timestamp","1562509952");
        signMap.put("url","http://47.92.115.105/memory-voice-h5/inspector.html");
        String sign = WxSignUtil.initJsSignBySHA1(signMap);
        System.out.println(sign);

    }
    /*@Override
    public WxOrderContext getUnifiedOrder(WxOrderRequest wxOrderRequest)throws Exception{
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("_-", "_")));

        String xmlMessage = XmlUtils.toXml(wxOrderRequest);
        log.info("调用wx统一下单的发送的XML为：{}",xmlMessage);
        xmlMessage = "<xml> <mch_id>1539306221</mch_id> <nonce_str><![CDATA[5d1cae2945e97]]></nonce_str> <body><![CDATA[记忆之声测试]]></body> <out_trade_no>201508061253461562160676</out_trade_no> <total_fee>88</total_fee> <notify_url><![CDATA[https://pay.weixin.qq.com/wxpay/pay.action]]></notify_url> <trade_type><![CDATA[JSAPI]]></trade_type> <openid><![CDATA[op41H42dy9HRmSrV4V_HmgFs5mQ0]]></openid> <spbill_create_ip><![CDATA[127.0.0.1]]></spbill_create_ip> <appid><![CDATA[wx4f6b226a68a2c8fd]]></appid> <sign><![CDATA[85EC6561687615C1E013AC729C3D036C]]></sign> </xml>";
        log.info("调用wx统一下单的发送的XML(false)为：{}",xmlMessage);
        String resXml = sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder",xmlMessage);
        log.info("调用wx统一下单的获取的结果为：{}",resXml);
        Map<String,String> resMap = parseXml(new ByteArrayInputStream(resXml.getBytes()));
        WxOrderContext wxOrderContext = WxOrderContext.builder()
                .return_code(resMap.get("return_code"))
                .return_msg(resMap.get("return_msg"))
                .result_code(resMap.get("result_code"))
                .err_code(resMap.get("err_code"))
                .err_code_des(resMap.get("err_code_des"))
                .trade_type(resMap.get("trade_type"))
                .prepay_id(resMap.get("prepay_id"))
                .build();
        log.info(wxOrderContext.toString());
        return wxOrderContext;
    }*/

    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(InputStream inputStream) throws Exception {

        if (inputStream == null){
            return null;
        }

        Map<String, String> map = new HashMap <String, String>();// 将解析结果存储在HashMap中
        SAXReader reader = new SAXReader();// 读取输入流
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();// 得到xml根元素
        List<Element> elementList = root.elements();// 得到根元素的所有子节点
        for (Element e : elementList) {        // 遍历所有子节点
            map.put(e.getName(), e.getText());
        }

        inputStream.close();        // 释放资源
        inputStream = null;

        return map;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        //System.out.println("post推送结果："+result);
        return result;
    }

}
