package com.mem.vo.business.base.service.impl;


import com.alibaba.fastjson.JSON;
import com.mem.vo.business.base.dao.ActivityDao;
import com.mem.vo.business.base.dao.SponsorDao;
import com.mem.vo.business.base.model.po.MtaBean;
import com.mem.vo.business.base.model.po.MtaBeans;
import com.mem.vo.business.base.model.po.MtaData;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.SponsorQuery;
import com.mem.vo.business.base.service.SponsorService;
import com.mem.vo.common.constant.AppEnum;
import com.mem.vo.common.dto.Page;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
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
*
* <br>
* <b>功能：</b>SponsorService<br>
*/
@Service("sponsorService")
public class  SponsorServiceImpl implements SponsorService {
    private final static Logger log= LogManager.getLogger(SponsorServiceImpl.class);


    @Resource
    private ActivityDao activityDao;

    @Resource
    private SponsorDao sponsorDao;

    @Override
    public int insert(Sponsor sponsor){
        return sponsorDao.insert(sponsor);
    }

    @Override
    public int updateById(Sponsor sponsor){
        return sponsorDao.updateById(sponsor);
    }

    @Override
    public int deleteById(List<Long> ids){
        return sponsorDao.deleteById(ids);
    }

    @Override
    public Sponsor findById(Long id){
        return sponsorDao.findById(id);
    }

    @Override
    public List<Sponsor> findByCondition(SponsorQuery query){
        return sponsorDao.findByCondition(query);
    }

    @Override
    public void findPageByCondition(Page page, SponsorQuery query){
        sponsorDao.findByCondition(page,query);
    }

    @Override
    public MtaData findHistory() throws URISyntaxException, IOException {
        String dateTime = System.currentTimeMillis() + "";
        String time = dateTime.substring(0, dateTime.length() - 3);
        String key = AppEnum.KEY.getCode();
        key = key.replace('-', '+').replace('_', '/');
        StringBuffer s = new StringBuffer(key);
        s.append("&").append("app_id=" + AppEnum.App_id.getCode());
        s.append("&").append("timestamp=" + time);
        String sign = DigestUtils.md5DigestAsHex(s.toString().getBytes());
        String per = "https://openapi.mta.qq.com/wx/v1";
        String url = per + "/analytics/real_time";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter("app_id", AppEnum.App_id.getCode());
        uriBuilder.addParameter("sign", sign);
        uriBuilder.addParameter("timestamp", time);
        URI uri = uriBuilder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
                MtaBeans myBean = JSON.parseObject(content, MtaBeans.class);
                return myBean.getData();
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

    @Override
    public MtaBean findArea(String start_time, String end_time) throws URISyntaxException, IOException {
        String dateTime = System.currentTimeMillis() + "";
        String time = dateTime.substring(0, dateTime.length() - 3);
        System.out.println(time);
        String key = AppEnum.KEY.getCode();
        key = key.replace('-', '+').replace('_', '/');
        StringBuffer s = new StringBuffer(key);
        s.append("&").append("app_id=" + AppEnum.App_id.getCode());
        s.append("&").append("end_time=" + end_time);
        s.append("&").append("start_time=" + start_time);
        s.append("&").append("timestamp=" + time);
        String sign = DigestUtils.md5DigestAsHex(s.toString().getBytes());
        System.out.println(sign);
        String per = "https://openapi.mta.qq.com/wx/v1";
        String url = per + "/analytics/area";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter("app_id", AppEnum.App_id.getCode());
        uriBuilder.addParameter("end_time", end_time);
        uriBuilder.addParameter("start_time", start_time);
        uriBuilder.addParameter("sign", sign);
        uriBuilder.addParameter("timestamp", time);
        URI uri = uriBuilder.build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
                MtaBean myBean = (MtaBean)JSON.parseObject(content, MtaBean.class);
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

    @Override
    public List<Sponsor> queryAllSponorByPhone(Long spid, Long activityid) {
        List<Sponsor> list = new ArrayList<>();
        if (spid != null) {
            Sponsor byId = sponsorDao.findById(spid);
            list.add(byId);
        } else if (activityid != null) {
            String splists = activityDao.findSplistToid(activityid);
            if (splists != null && splists != "") {
                String[] split = splists.split("~");
                for (String s : split) {
                    Sponsor sponsor = findById(Long.valueOf(s));
                    if (!sponsor.getIsshow().toString().equals("0")) {
                        if (list.size() < 9) {
                            list.add(sponsor);
                        } else {
                            return list;
                        }
                    }
                }
            }
        } else {
            list = sponsorDao.findByConditionbyishow();
        }
        return list;
    }

    @Override
    public List<String> querySponorPictureByactid(Long activityid) {
        List<String> list = new ArrayList<>();
        String splists = activityDao.findSplistToid(activityid);
        if (splists != null && splists != "") {
            String[] split = splists.split("~");
            for (String s : split) {
                Sponsor sponsor = findById(Long.valueOf(s));
                if (!sponsor.getIsshow().toString().equals("0")) {
                    if (list.size() < 9) {
                        list.add(sponsor.getThumburl());
                    } else {
                        return list;
                    }
                }
            }
        }
        return list;
    }

    @Override
    public Sponsor getSponsorOne(Long id) {
        Sponsor sponsorOne = sponsorDao.getSponsorOne(id);
        List<String> a = sponsorDao.getSponsorPrize(id);
        if (a != null) {
            sponsorOne.setPrize(a);
        }
        return sponsorOne;
    }
}
