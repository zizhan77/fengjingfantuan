package com.mem.vo.common.util;

import com.mem.vo.business.biz.model.dto.WxOrderContext;
import com.mem.vo.business.biz.model.dto.WxOrderRequest;
import com.mem.vo.business.biz.model.dto.WxOrderResign;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;


/**
 * 
 * @date     创建时间：2015年7月9日 下午10:46:38
 * @version 1.0
 * 
 * http://blog.csdn.net/dong_18383219470/article/details/54340586
 */
public class PayUtil {
	/**
	 * 设置支付成功后返回的结果
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
	}
	
	public static void main(String[] args) {
		String openId = "oUWq-vjtj_GahvLxNaCSi-t2srQ4";
		String appid = "wxcb067842693f2917";
		String appsecret = "38dca97e35480b155cb8b65cf17c0020";
		String mchId = "1253700101";
		//Map<String,Object> map = sendPayment(openId, appid, mchId, "113.27.166.219", appsecret, "你妹", "fsdfasdfasdaads",new BigDecimal(1), "1");
		//System.out.println(getMapToXML(map));
        ////System.out.println(NonceStr().length());
		//SendPayment("苹果","20170106113324",1,"1");  
    }

    /* 
     * 发起支付请求 
     * body 商品描述 
     * out_trade_no 订单号 
     * total_fee    订单金额        单位  元 
     * product_id   商品ID 
     */
    public static Map<String,Object> sendPayment(WxOrderRequest r){

        return sendPayment(r.getOpenid(),r.getAppid(),r.getMch_id(),
                r.getSpbill_create_ip(),r.getShopKey(),r.getBody(),
                r.getOut_trade_no(),new BigDecimal(r.getTotal_fee()),
                "",r.getNotify_url());
    }

    public static Map<String,Object> sendPayment(String openId, String appid,String mchId,String ip,String key,String body,String out_trade_no,BigDecimal total_fee,String product_id,String notify_url){
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";  
        String xml = wXParamGenerate(openId, appid, mchId, ip, key, body, out_trade_no, total_fee, product_id,notify_url);
        String res = httpsRequest(url,"POST",xml);
        Map<String,Object> data = null;  
        try {  
            data = doXMLParse(res);  
        } catch (Exception e) {
            e.printStackTrace();
        }  
        return data;  
    }  
      
    public static String nonceStr(){
        String res = UUID.randomUUID().toString().substring(0,30);
        return res;  
    }  
      
     public static String getIp() {  
        InetAddress ia=null;  
        try {  
            ia=InetAddress.getLocalHost();  
            String localip=ia.getHostAddress();  
            return localip;  
        } catch (Exception e) {  
            return null;  
        }  
    }  

       
     public static String getSign(Map<String, Object> param,String key){  
         ArrayList<String> list = new ArrayList<String>();  
         for(Map.Entry<String,Object> entry:param.entrySet()){  
             if(entry.getValue()!=""){
                 list.add(entry.getKey() + "=" + entry.getValue() + "&");
             }  
         }  
         int size = list.size();  
         String [] arrayToSort = list.toArray(new String[size]);  
         Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);  
         StringBuilder sb = new StringBuilder();  
         for(int i = 0; i < size; i ++) {  
             sb.append(arrayToSort[i]);  
         }  
         String result = sb.toString();  
         result += "key=" + key;  
         //Util.log("Sign Before MD5:" + result);  
         result = WxSignUtil.encodeByMD5(result).toUpperCase();
         //Util.log("Sign Result:" + result);  
         return result;  
     } 
     
     //Map转xml数据  
     public static String getMapToXML(Map<String,Object> param){  
         StringBuffer sb = new StringBuffer();  
         sb.append("<xml>");  
         for (Map.Entry<String,Object> entry : param.entrySet()) {   
                sb.append("<"+ entry.getKey() +">");  
                sb.append((String)entry.getValue());  
                sb.append("</"+ entry.getKey() +">");  
        }    
         sb.append("</xml>");  
         return sb.toString();  
     }

    //微信统一下单参数设置
    public static WxOrderResign wXResign(WxOrderContext context, WxOrderRequest wxOrderRequest){
        WxOrderResign signDto = new WxOrderResign();
        signDto = BeanCopyUtil.copyProperties(wxOrderRequest, WxOrderResign.class);
        SortedMap<String,Object> signMap = new TreeMap<> ();
        signMap.put("appId",signDto.getAppid());
        signMap.put("timeStamp",signDto.getTimeStamp());
        signMap.put("nonceStr",wxOrderRequest.getNonce_str());
        signMap.put("package","prepay_id="+context.getPrepay_id());
        signMap.put("signType",signDto.getSignType());
        signDto.setA_package(context.getPrepay_id());
        System.out.println(signDto.getAppid()+" "
                +signDto.getTimeStamp()+" "
                +wxOrderRequest.getNonce_str()+" "
                +context.getPrepay_id()+" "
                +signDto.getSignType()+" "
                +wxOrderRequest.getShopKey());
        signDto.setSign(getSign(signMap,wxOrderRequest.getShopKey()));
        return signDto;
    }
    //微信统一下单参数设置  
    public static String wXParamGenerate(String openId,String appid,String mchId,String ip,String key, String description,String out_trade_no,BigDecimal total_fee,String product_id,String notify_url){
        int fee = total_fee.intValue();
        Map<String,Object> param = new HashMap<String,Object>();  
        param.put("openid", openId);  
        param.put("appid", appid);  
        param.put("mch_id", mchId);  
        param.put("nonce_str",nonceStr());  
        param.put("body", description);
        param.put("out_trade_no",out_trade_no);  
        param.put("total_fee", fee+"");  
        param.put("spbill_create_ip", ip);  
        param.put("notify_url",  notify_url);
        param.put("trade_type", "JSAPI");
          
        String sign = getSign(param,key);  
          
        param.put("sign", sign);  
        return getMapToXML(param);  
    }  
      
    //发起微信支付请求  
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {    
      try {    
          URL url = new URL(requestUrl);    
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
            
          conn.setDoOutput(true);    
          conn.setDoInput(true);    
          conn.setUseCaches(false);    
          // 设置请求方式（GET/POST）    
          conn.setRequestMethod(requestMethod);    
          conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");    
          // 当outputStr不为null时向输出流写数据    
          if (null != outputStr) {    
              OutputStream outputStream = conn.getOutputStream();    
              // 注意编码格式    
              outputStream.write(outputStr.getBytes("UTF-8"));    
              outputStream.close();    
          }    
          // 从输入流读取返回内容    
          InputStream inputStream = conn.getInputStream();    
          InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");    
          BufferedReader bufferedReader = new BufferedReader(inputStreamReader);    
          String str = null;  
          StringBuffer buffer = new StringBuffer();    
          while ((str = bufferedReader.readLine()) != null) {    
              buffer.append(str);    
          }    
          // 释放资源    
          bufferedReader.close();    
          inputStreamReader.close();    
          inputStream.close();    
          inputStream = null;    
          conn.disconnect();    
          return buffer.toString();    
      } catch (ConnectException ce) {    
          //System.out.println("连接超时：{}"+ ce);    
      } catch (Exception e) {    
          //System.out.println("https请求异常：{}"+ e);    
      }    
      return null;    
    }    
        
    //xml解析    
    public static Map<String, Object> doXMLParse(String strxml) throws Exception {    
          strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");    
          if(null == strxml || "".equals(strxml)) {    
              return null;    
          }    
              
          Map<String,Object> m = new HashMap<String,Object>();     
          InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));    
          SAXBuilder builder = new SAXBuilder();
          Document doc = builder.build(in);
          Element root = doc.getRootElement();
          List list = root.getChildren();    
          Iterator it = list.iterator();    
          while(it.hasNext()) {    
              Element e = (Element) it.next();    
              String k = e.getName();    
              String v = "";    
              List children = e.getChildren();    
              if(children.isEmpty()) {    
                  v = e.getTextNormalize();    
              } else {    
                  v = getChildrenText(children);    
              }    
                  
              m.put(k, v);    
          }    
              
          //关闭流    
          in.close();     
          return m;    
    }    
        
    public static String getChildrenText(List children) {    
          StringBuffer sb = new StringBuffer();    
          if(!children.isEmpty()) {    
              Iterator it = children.iterator();    
              while(it.hasNext()) {    
                  Element e = (Element) it.next();    
                  String name = e.getName();    
                  String value = e.getTextNormalize();    
                  List list = e.getChildren();    
                  sb.append("<" + name + ">");    
                  if(!list.isEmpty()) {    
                      sb.append(getChildrenText(list));    
                  }    
                  sb.append(value);    
                  sb.append("</" + name + ">");    
              }    
          }     
          return sb.toString();    
    }  
}
