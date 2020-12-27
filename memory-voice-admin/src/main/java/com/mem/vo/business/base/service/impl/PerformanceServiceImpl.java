package com.mem.vo.business.base.service.impl;


import com.mem.vo.business.base.dao.BasicPlaceDao;
import com.mem.vo.business.base.dao.PerformanceDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.vo.performance.BasicArtistVo;
import com.mem.vo.business.biz.model.vo.performance.BasicPlaceVo;
import com.mem.vo.business.biz.model.vo.performance.PerformanceVo;
import com.mem.vo.business.biz.model.vo.performance.ShowDetail;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.PerformanceStatus;
import com.mem.vo.common.constant.PerformanceType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <br>
 * <b>功能：</b>PerformanceService<br>
 */
@Service("performanceService")
public class PerformanceServiceImpl implements PerformanceService {

    private final static Logger log = LogManager.getLogger(PerformanceServiceImpl.class);


    @Resource
    private PerformanceDao performanceDao;

    @Resource
    private BasicPlaceDao basicPlaceDao;

    @Resource
    private BasicAddressService basicAddressService;

    @Resource
    private BasicArtistService basicArtistService;

    @Resource
    private TicketGearService ticketGearService;

    @Resource
    private PerformanceShowService performanceShowService;

    @Override
    public int insert(Performance performance) {
        return performanceDao.insert(performance);
    }

    @Override
    public int updateById(Performance performance) {
        return performanceDao.updateById(performance);
    }

    @Override
    public int deleteById(Long id) {
        return performanceDao.deleteById(id);
    }

    @Override
    public Performance findById(Long id) {
        return performanceDao.findById(id);
    }

    @Override
    public List<Performance> findByCondition(PerformanceQuery query) {
        return performanceDao.findByCondition(query);
    }

    @Override
    public Page<Performance> findPageByCondition(Page page, PerformanceQuery query) {
        List<Performance> list = performanceDao.findByCondition(page, query);
        page.setData(list);
        return page;
    }

    @Override
    public ShowDetail getDetail(Long id) {

        //token 及用户权限校验
        Performance performance = findById(id);

        return getShowDetail( performance);
    }

    public ShowDetail getShowDetail(Performance performance) {

        ShowDetail showDetail = new ShowDetail();

        BizAssert.notNull(performance, BizCode.BIZ_1007.getMessage());
        BasicPlace place  =  basicPlaceDao.findById(performance.getPlaceId());

        PerformanceVo performanceVo = BeanCopyUtil.copyProperties(performance,PerformanceVo.class);

        if (performanceVo.getStatus() != null) {
            performanceVo.setStatusStr(PerformanceStatus.getNameByCode(performance.getStatus()));
        }

        if (performanceVo.getStartSaleDate() != null) {
            performanceVo.setStartSaleDateStr(DateUtil.formatDateStr(performance.getStartSaleDate()));
        }

        if (performanceVo.getCreateTime() != null) {
            performanceVo.setCreateTimeStr(DateUtil.formatDateStr(performance.getCreateTime()));
        }

        if(performanceVo.getPerformanceType()!=null){
            performanceVo.setPerformanceTypeStr(PerformanceType.getNameByCode(performanceVo.getPerformanceType()));
        }

        if(StringUtils.isNotBlank(performanceVo.getArtistIds())){
            List<BasicArtistVo> vos = new ArrayList<>();
            String[] artids = performanceVo.getArtistIds().split("~");
            for(String s: artids){
                BasicArtist artist = basicArtistService.findById(Long.valueOf(s));
                BizAssert.notNull(artist,BizCode.BIZ_1015.getMessage()+"id: "+s);
                vos.add(BeanCopyUtil.copyProperties(artist,BasicArtistVo.class));
            }
            performanceVo.setArtistVoList(vos);
        }


        if(StringUtils.isNotBlank(performanceVo.getKeyWords())){
            List<String> keywordList = new ArrayList<>();
            String[] keywords = performanceVo.getKeyWords().split("~");
            for(String s: keywords){
                keywordList.add(s);
            }
            performanceVo.setKeyWordsList(keywordList);
        }

        BigDecimal lowPrice = BigDecimal.ZERO;
        if(StringUtils.isNotBlank(performanceVo.getTicketGearIds())){
            List<TicketGear> ticketGearList = new ArrayList<>();
            String[] ticketGearArr = performanceVo.getTicketGearIds().split("~");
            for(String s: ticketGearArr){
                TicketGear gear = ticketGearService.findById(Long.valueOf(s));
                if(lowPrice.compareTo(BigDecimal.ZERO)==0){
                    lowPrice = gear.getPrice();
                }else{
                    if(lowPrice.compareTo(gear.getPrice())>0){
                        lowPrice = gear.getPrice();
                    }
                }
                ticketGearList.add(gear);
            }
            //查询票档详情，并返回
            performanceVo.setTicketGearList(ticketGearList);
        }

        performanceVo.setLowPrice(lowPrice);

        List<Date> showTimeList =  new ArrayList<>();
        if(StringUtils.isNotBlank(performanceVo.getShowIds())){
            List<PerformanceShow> showList = new ArrayList<>();
            String[] showArr = performanceVo.getShowIds().split("~");
            for(String s: showArr){
                PerformanceShow show = performanceShowService.findById(Long.valueOf(s));
                if(show.getStartTime()!=null){
                    show.setStartTimeStr(DateUtil.formatDateStr(show.getStartTime()));
                }
                if(show.getEndTime()!=null){
                    show.setEndTimeStr(DateUtil.formatDateStr(show.getEndTime()));
                }
                showList.add(show);
                showTimeList.add(show.getStartTime());

            }

            Collections.sort(showTimeList);
            performanceVo.setShowTime(showTimeList.get(0));
            if(performanceVo.getShowTime()!=null){
                performanceVo.setShowTimeStr(DateUtil.formatDateStr(performanceVo.getShowTime()));
            }
            //查询场次详情，并返回
            performanceVo.setShowList(showList);
        }
        Date  firstShowTime =showTimeList.get(0);
        BizAssert.notNull(firstShowTime,"获取第一场演出时间为空");
        performanceVo.setSaleEndTimeStamp(firstShowTime.getTime());
        Date  endShowTime = showTimeList.get(showTimeList.size()-1);
        //状态转换
        Integer status = 0;
        status = getStatus(performanceVo.getStartSaleDate(), firstShowTime, status);

        performanceVo.setStatus(status);


        //补充票价的起始价格
        BasicPlaceVo basicPlaceVo = BeanCopyUtil.copyProperties(place,BasicPlaceVo.class);
        basicPlaceVo.setOneAddressName(basicAddressService.findNameByCode(basicPlaceVo.getOneAddress()));
        basicPlaceVo.setTwoAddressName(basicAddressService.findNameByCode(basicPlaceVo.getTwoAddress()));
        basicPlaceVo.setThreeAddressName(basicAddressService.findNameByCode(basicPlaceVo.getThreeAddress()));


        showDetail.setPerformanceVo(performanceVo);
        showDetail.setPlaceInfo(basicPlaceVo);

        return showDetail;
    }

    public Integer getStatus(Date startSaleDate, Date firstShowTime, Integer status) {
        /**
         * 开售时间为空   & 当前时间< 首场演出时间 =》 预售（未确定时间）
         *
         * 开售时间不为空 & 当前时间< 首创演出时间  =》 预售（已确定开售时间）
         *
         * 开售时间不为空 & 当前时间 >开售时间 & 当前时间< 首场演出时间   =》开售
         *
         * 开售时间不为空 & 当前时间 > 首场演出开始时间    =》 销售结束
         */
        if(startSaleDate==null ){
            if(new Date().compareTo(startSaleDate)<0){
                status = PerformanceStatus.PRE_SALE_WITHOUT_TIME.getCode();
            }
        }else {
            if(new Date().compareTo(startSaleDate)<0){
                status = PerformanceStatus.PRE_SALE_HAS_TIME.getCode();
            }

            if(new Date().compareTo(startSaleDate)>0 && new Date().compareTo(firstShowTime)<0){
                status = PerformanceStatus.SALEING.getCode();
            }

            if(new Date().compareTo(firstShowTime)>0){
                status = PerformanceStatus.HAS_EXPIRED.getCode();
            }

        }
        return status;
    }

    @Override
    public List<ShowDetail> getList() {

        PerformanceQuery query = new PerformanceQuery();
        query.setIsDelete(0);
        List<Performance> list = findByCondition(query);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }

        List<ShowDetail> performanceVos = new ArrayList<>(list.size());
        list.forEach(item->{
            ShowDetail showDetail = getShowDetail(item);
            performanceVos.add(showDetail);
        });
        return performanceVos;
    }

    @Override
    public Page<Performance> findByPageVo(Page page, PerformanceQuery performanceQuery) {
        List<Performance> list = performanceDao.findByVo(page, performanceQuery);
        page.setData(list);
        return page;
    }

    @Override
    public List<Performance> selectByUserId(Integer userId) {
        return performanceDao.selectByUserId(userId);
    }


    public static void main(String[] args) {
        List<Date> showTimeList = new ArrayList<>();
        showTimeList.add(DateUtil.parseDatetime("2019-08-01"));
        showTimeList.add(DateUtil.parseDatetime("2019-01-01"));
        showTimeList.add(DateUtil.parseDatetime("2019-06-01"));

        Collections.sort(showTimeList);

        System.out.println(DateUtil.formatDateShort(showTimeList.get(0)));
        System.out.println(DateUtil.formatDateShort(showTimeList.get(showTimeList.size()-1)));
    }
}
