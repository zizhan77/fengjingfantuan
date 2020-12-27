package com.mem.vo.business.base.service.impl;


import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.mem.vo.business.base.dao.BasicPlaceDao;
import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.BasicPlaceQuery;
import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.base.service.BasicPlaceService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * <br>
 * <b>功能：</b>BasicPlaceService<br>
 */
@Service("basicPlaceService")
@Transactional
public class BasicPlaceServiceImpl implements BasicPlaceService {
    private final static Logger log = LogManager.getLogger(BasicPlaceServiceImpl.class);


    @Resource
    private BasicPlaceDao basicPlaceDao;

    @Override
    public int insert(BasicPlace basicPlace) {
        return basicPlaceDao.insert(basicPlace);
    }

    @Override
    public int updateById(BasicPlace basicPlace) {
        return basicPlaceDao.updateById(basicPlace);
    }

    @Override
    public int deleteById(Long id) {
        return basicPlaceDao.deleteById(id);
    }

    @Override
    public BasicPlace findById(Long id) {
        return basicPlaceDao.findById(id);
    }

    @Override
    public List<BasicPlace> findByCondition(BasicPlaceQuery query) {
        return basicPlaceDao.findByCondition(query);
    }

    @Override
    public Page<BasicPlace> findPageByCondition(Page page, BasicPlaceQuery query) {
        List<BasicPlace> list = basicPlaceDao.findByCondition(page, query);
        BizAssert.notEmpty(list, BizCode.BIZ_1102.getMessage());
        page.setData(list);
        return page;
    }

    @Override
    public List<String> findAllCity() {
        return basicPlaceDao.findAllCity();
    }

    @Override
    public List<BasicPlace> findByIdList(List<Long> list) {
        return basicPlaceDao.findByIdList(list);
    }

    @Override
    public List<BasicPlace> findByPlaceId(long parseLong) {
        return null;
    }

    @Override

    public MtaBean findHistory(String startTime,String endTime) throws Exception {
        String dateTime = new Date().getTime() + "";
        String time = dateTime.substring(0, dateTime.length() - 3);
        String key = "5c6cc1293c37ebe624d0ff64ec39f749";
        key = key.replace('-', '+').replace('_', '/');
        StringBuffer s = new StringBuffer(key);
        s.append("&").append("app_id=500687204");
        s.append("&").append("end_time="+endTime);
        s.append("&").append("start_time="+startTime);
        s.append("&").append("timestamp=" + time);
        String sign = DigestUtils.md5DigestAsHex(s.toString().getBytes());
        String per = "https://openapi.mta.qq.com/wx/v1";
        //System.out.println(per + "/analytics/history" + "?" + "timestamp=" + time + "&" + "app_id=500687204" + "&" + "start_time=2019-04-01" + "&" + "end_time=2019-08-29" + "&" + "app_id=500687204" + "&" + "sign=" + sign);
        String url = per + "/analytics/history";
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        // 定义请求的参数
        uriBuilder.addParameter("app_id", "500687204");
        uriBuilder.addParameter("end_time", endTime);
        uriBuilder.addParameter("start_time", startTime);
        uriBuilder.addParameter("sign", sign);
        uriBuilder.addParameter("timestamp", time);
        URI uri = uriBuilder.build();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);
        //response 对象
        CloseableHttpResponse response = null;
        try {
            // 执行http get请求
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                MtaBean myBean = JSON.parseObject(content, MtaBean.class);
                return myBean;
            } else {
                //请求没成功，不解析，再做处理。
                //变成返回体信息
                System.out.println("失败");
                return null;
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
}

