package com.mem.vo.business.base.service.impl;


import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.mem.vo.business.base.dao.BasicPlaceDao;
import com.mem.vo.business.base.dao.PlaceArtistMapper;
import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.BasicPlaceQuery;
import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.base.model.vo.PlaceArtistVO;
import com.mem.vo.business.base.service.BasicPlaceService;
import com.mem.vo.business.biz.model.vo.performance.BasicPlaceVo;
import com.mem.vo.common.constant.AppEnum;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
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
    private PlaceArtistMapper placeArtistMapper;

    @Resource
    private BasicPlaceDao basicPlaceDao;

    @Override
    public int insert(BasicPlaceVo basicPlaceVo) {
        BizAssert.notNull(basicPlaceVo, BizCode.PARAM_NULL.getMessage());
        BasicPlace basicPlace = (BasicPlace) BeanCopyUtil.copyProperties(basicPlaceVo, BasicPlace.class);
        int insert = this.basicPlaceDao.insert(basicPlace);
        System.out.println("主键"+ basicPlace.getId());
        if (!basicPlaceVo.getArtistList().isEmpty()) {
            List<String> list = new ArrayList<>();
            for (PlaceArtistVO placeArtist : basicPlaceVo.getArtistList()) {
                list.add(placeArtist.getArtistId() + "");
            }
            int i = this.placeArtistMapper.insertList(list, basicPlace.getId().intValue());
            insert = i;
        }
        return insert;
    }

    @Override
    public int updateById(BasicPlaceVo basicPlaceVo) {
        BizAssert.notNull(basicPlaceVo, BizCode.PARAM_NULL.getMessage());
        BasicPlace basicPlace = (BasicPlace)BeanCopyUtil.copyProperties(basicPlaceVo, BasicPlace.class);
        int i = basicPlaceDao.updateById(basicPlace);
        if (i == 0) {
            throw new BizException("修改失败");
        }
        int s = placeArtistMapper.deleteByPlaceId(basicPlaceVo.getId());
        if (!basicPlaceVo.getArtistList().isEmpty()) {
            List<String> list = new ArrayList<>();
            for (PlaceArtistVO placeArtist : basicPlaceVo.getArtistList()) {
                System.out.println(placeArtist);
                list.add(placeArtist.getArtistId() + "");
            }
            i = placeArtistMapper.insertList(list, basicPlaceVo.getId().intValue());
        }
        return i;
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

//    @Override
//    public List<BasicPlace> findByPlaceId(long parseLong) {
//        return null;
//    }

    @Override

    public MtaBean findHistory(String startTime,String endTime) throws Exception {
        String dateTime = System.currentTimeMillis() + "";
        String time = dateTime.substring(0, dateTime.length() - 3);
        String key = AppEnum.KEY.getCode();
        key = key.replace('-', '+').replace('_', '/');
        StringBuffer s = new StringBuffer(key);
        s.append("&").append("app_id=" + AppEnum.App_id.getCode());
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
        uriBuilder.addParameter("app_id", AppEnum.App_id.getCode());
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
            }
            System.out.println("失败");
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
}

