package com.mem.vo.controller.performance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.PerformanceQuery;
import com.mem.vo.business.base.model.po.Seats;
import com.mem.vo.business.base.model.po.SeatsQuery;
import com.mem.vo.business.base.model.po.ShowSeats;
import com.mem.vo.business.base.model.po.TicketGear;
import com.mem.vo.business.base.model.po.TicketStore;
import com.mem.vo.business.base.service.AreaService;
import com.mem.vo.business.base.service.BasicPlaceService;
import com.mem.vo.business.base.service.OrderService;
import com.mem.vo.business.base.service.PerformanceService;
import com.mem.vo.business.base.service.SeatsService;
import com.mem.vo.business.base.service.ShowSeatsService;
import com.mem.vo.business.base.service.TicketGearService;
import com.mem.vo.business.base.service.TicketStoreService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.vo.performance.PerformanceVo;
import com.mem.vo.business.biz.model.vo.performance.ShowDetail;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.EnableStatus;
import com.mem.vo.common.constant.PerformanceStatus;
import com.mem.vo.common.constant.SeatStatus;
import com.mem.vo.common.constant.VmOptionType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import com.mem.vo.common.util.DateUtil;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author litongwei
 * @description: 演出
 * @date 2019/5/25 15:43
 */

@RestController
@RequestMapping("/performance")
@Slf4j
public class PerformanceController {

    @Resource
    private PerformanceService performanceService;

    @Resource
    private TokenService tokenService;

    @Resource
    private TicketStoreService ticketStoreService;

    @Resource
    private ShowSeatsService showSeatsService;

    @Resource
    private SeatsService seatsService;

    @Resource
    private BasicPlaceService basicPlaceService;

    @Resource
    private AreaService areaService;

    @Resource
    private TicketGearService ticketGearService;

    @Resource
    private OrderService orderService;

    @PostMapping("/getDetail")
    public ResponseDto<ShowDetail> getDetail(@RequestHeader("token") String token, Long id) {

        ResponseDto<ShowDetail> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(id, BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(performanceService.getDetail(id));

        } catch (BizException e) {

            log.error("获取演出详情异常，参数:{},原因：{}", id, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取演出详情异常，参数:{}", id, e);
            return responseDto.failSys();

        }

    }

    @PostMapping("/getList")
    public ResponseDto<List<ShowDetail>> getList(@RequestHeader("token") String token) {
        ResponseDto<List<ShowDetail>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(performanceService.getList());

        } catch (BizException e) {

            log.error("获取演出列表异常,原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取演出列表异常", e);
            return responseDto.failSys();
        }
    }


    public static void main(String[] args) {
        Page page = new Page();
        page.setData(new ArrayList<>());
        page.setTotal(0);
        page.setLimit(0);
        page.setPage(0);
        page.setOrderByField("");
        page.setOrderByType("");

        System.out.println(JsonUtil.toJson(page));

        PerformanceVo performanceVo = new PerformanceVo();
        performanceVo.setId(0L);
        performanceVo.setTitle("");
        performanceVo.setDescription("");
        performanceVo.setShowTime(new Date());
        performanceVo.setThumbUrl("");
        performanceVo.setPlaceId(0L);
        performanceVo.setStatus(0);
        performanceVo.setLowPrice(new BigDecimal("0"));

        System.out.println(JsonUtil.toJson(performanceVo));

    }

    @PostMapping("/pc/getListByPage")
    public ResponseDto<JSONObject> getListPage(
            PerformanceQuery performanceQuery) {

        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        try {
            JSONObject jsonObject = new JSONObject();
            Page page = new Page();
            page.setPage(performanceQuery.getPage());
            page.setLimit(performanceQuery.getLimit());

            Page<Performance> resPage = performanceService.findByPageVo(page, performanceQuery);
            List<Performance> resList = resPage.getData();
            List<ShowDetail> showDetails = new ArrayList<>();
            resList.forEach(item -> {
                ShowDetail detail = performanceService.getShowDetail(item);
                showDetails.add(detail);
            });

            jsonObject.put("total", resPage.getTotal());
            jsonObject.put("list", showDetails);

            return responseDto.successData(jsonObject);

        } catch (BizException e) {

            log.error("分页获取演出列表异常,参数1：{}，参数2：{} , 原因：{}", JsonUtil.toJson(performanceQuery), JsonUtil.toJson(""),
                    e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("分页获取演出列表异常，参数1：{}，参数2：{}", JsonUtil.toJson(performanceQuery), JsonUtil.toJson(""), e);
            return responseDto.failSys();
        }

    }


    @PostMapping("/wx/getListByPage")
    @CommonExHandler(key = "wx查询演出列表接口")
    public ResponseDto<JSONObject> getListPageForWx(
            PerformanceQuery performanceQuery) {

        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        JSONObject jsonObject = new JSONObject();
        Page page = new Page();
        page.setPage(performanceQuery.getPage());
        page.setLimit(performanceQuery.getLimit());
        performanceQuery.setIsDelete(0);
        performanceQuery.setEnable(EnableStatus.ON.getCode()); //默认启用
        Page<Performance> resPage = performanceService.findByPageVo(page, performanceQuery);
        List<Performance> resList = resPage.getData();
        List<ShowDetail> showDetails = new ArrayList<>();
        resList.forEach(item -> {
            ShowDetail detail = performanceService.getShowDetail(item);
            if (PerformanceStatus.wxEnableSet.contains(detail.getPerformanceVo().getStatus())) {
                showDetails.add(detail);
            }
        });

        jsonObject.put("total", resPage.getTotal());
        jsonObject.put("list", showDetails);

        return responseDto.successData(jsonObject);

    }

    //需要优化，加钱;座位、库存相关需要排他锁，加钱
    @PostMapping("/addPerformance")
    @Transactional
    @CommonExHandler(key = "新增演出")
    public ResponseDto<Boolean> addPerformance(@RequestHeader("token") String token,
                                               PerformanceQuery performanceQuery) {
        ResponseDto<Boolean> responseDto = ResponseDto.successDto();


        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        Performance performance = BeanCopyUtil.copyProperties(performanceQuery, Performance.class);
        performance.setCreateUser(commonLoginContext.getBizCode());
        performance.setUpdateUser(performance.getCreateUser());
        performance.setEnable(EnableStatus.ON.getCode()); //默认启用
        if (StringUtils.isNotBlank(performanceQuery.getShowTimeStr())) {
            performance.setShowTime(DateUtil.parseDatetime(performanceQuery.getShowTimeStr()));
        }

        if (StringUtils.isNotBlank(performanceQuery.getStartSaleDateStr())) {
            performance.setStartSaleDate(DateUtil.parseDatetime(performanceQuery.getStartSaleDateStr()));
        }
        if (VmOptionType.INSERT.getCode().equals(performanceQuery.getOptType())) {
            int performanceId = performanceService.insert(performance);
            //生成库存
            List<TicketStore> ticketStoreList = generateTicketStore(performanceQuery.getPlaceId(), performanceQuery.getShowIds(),
                    performanceQuery.getTicketGearIds());

            for (TicketStore ticketStore : ticketStoreList) {
                ticketStore.setPerformanceId(performance.getId());
                ticketStoreService.insert(ticketStore);
            }

            //按照演出场次初始化座位
            if (basicPlaceService.findById(performanceQuery.getPlaceId()).getEnable().equals(1)) {
                generateShowSeatsAndTicketStore(performanceId,
                        performanceQuery.getShowIds(), performanceQuery.getPlaceId());
            }
        } else {
            Performance originPerformance = performanceService.findById(performance.getId());
            BizAssert.notNull(originPerformance, "查询原始演出为空，无法修改");

            if (StringUtils.isNotBlank(originPerformance.getTicketGearIds())) {
                if (!originPerformance.getTicketGearIds().equals(performance.getTicketGearIds())) {
                    throw new BizException("票档不可以修改");
                }
            }

            if (StringUtils.isNotBlank(originPerformance.getShowIds())) {
                if (!originPerformance.getShowIds().equals(performance.getShowIds())) {
                    throw new BizException("场次不可以修改");
                }
            }
            performanceService.updateById(performance);

        }
        return responseDto.successData(true);

    }

    private List<TicketStore> generateTicketStore(Long placeId, String showIds, String ticketGearIds) {

        BasicPlace basicPlace = basicPlaceService.findById(placeId);
        BizAssert.notNull(basicPlace, "查询场地为空");
        BizAssert.hasText(showIds, "场次列表为空");
        BizAssert.hasText(ticketGearIds, "票档列表为空");
        List<TicketStore> ticketStoreList = new ArrayList<>();
        String[] showArr = showIds.split("~");
        String[] geatArr = ticketGearIds.split("~");

        BizAssert.isTrue(showArr != null && showArr.length > 0, BizCode.BIZ_1017.getMessage());
        BizAssert.isTrue(geatArr != null && geatArr.length > 0, BizCode.BIZ_1016.getMessage());
        for (String showid : showArr) {
            for (String gearid : geatArr) {
                TicketStore ticketStore = new TicketStore();
                ticketStore.setShowId(Long.valueOf(showid));
                ticketStore.setTicketGearId(Long.valueOf(gearid));
                TicketGear ticketGear = ticketGearService.findById(Long.valueOf(gearid));
                BizAssert.notNull(ticketGear, "查询票档为空");
                //补全座位数量
                if (basicPlace.getChooseSeat() != null && basicPlace.getChooseSeat() == 1) {
                    SeatsQuery seatsQuery = new SeatsQuery();
                    seatsQuery.setPlaceId(basicPlace.getId());
                    seatsQuery.setAreaId(ticketGear.getAreaId());
                    seatsQuery.setStatus(SeatStatus.ONSALE.getCode());

                    List<Seats> seatses = seatsService.findByCondition(seatsQuery);
                    BizAssert.notEmpty(seatses, "查询座位为空");
                    ticketStore.setStoreNum(seatses.size());
                }

                ticketStoreList.add(ticketStore);
            }
        }
        return ticketStoreList;

    }


    private void generateShowSeatsAndTicketStore(int performanceId, String showIds, Long placeId) {
        BizAssert.hasText(showIds, "场次列表为空");
        String[] showArr = showIds.split("~");
        SeatsQuery seatsQuery = new SeatsQuery();
        seatsQuery.setPlaceId(placeId);
        List<Seats> sl = seatsService.findByCondition(seatsQuery);
        BizAssert.isTrue(showArr != null && showArr.length > 0, BizCode.BIZ_1017.getMessage());
        for (String showid : showArr) {
            for (Seats s : sl) {
                ShowSeats ss = BeanCopyUtil.copyProperties(s, ShowSeats.class);
                ss.setPerformanceId(Long.parseLong(performanceId + ""));
                ss.setShowId(Long.valueOf(showid));
                showSeatsService.insert(ss);
            }
        }

    }

    @PostMapping("updateEnable")
    @CommonExHandler(key = "修改演出启用停用状态")
    public ResponseDto<Boolean> updateEnable(Long performanceId, Integer enable) {
        Performance performance = new Performance();
        /*if (EnableStatus.OFF.getCode().equals(enable)) {
            //是否生成库存已经售出，是否生成订单 ,否则需要删除库存数据
            OrderQuery query = new OrderQuery();
            query.setPerformanceId(performanceId);
            query.setIsDelete(0);
            List<Order> orderList = orderService.findByCondition(query);
            BizAssert.isTrue(CollectionUtils.isEmpty(orderList), "已经生成订单，无法删除");

            //判断库存
            TicketStoreQuery ticketStoreQuery = new TicketStoreQuery();
            ticketStoreQuery.setPerformanceId(performanceId);
            ticketStoreQuery.setIsDelete(0);
            List<TicketStore> ticketStoreList = ticketStoreService.findByCondition(ticketStoreQuery);
            if (CollectionUtils.isNotEmpty(ticketStoreList)) {
                for (TicketStore ticketStore : ticketStoreList) {
                    //库存不为0  并且 有售出了
                    if (ticketStore.getStoreNum() > 0 && ticketStore.getSaleNum() > 0) {
                        throw new BizException("票已经售出，请查看对应的库存");
                    }
                }
            }
        }*/
        performance.setId(performanceId);
        performance.setEnable(enable);
        performanceService.updateById(performance);
        return new ResponseDto<>().successData(true);
    }

}
