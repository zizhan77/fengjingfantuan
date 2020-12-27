package com.mem.vo.controller.exchangeCode;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.dao.ExchangeCodeMainDao;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.vo.exchangecode.CheckTicketVo;
import com.mem.vo.business.biz.model.vo.exchangecode.ExRecordVo;
import com.mem.vo.business.biz.model.vo.exchangecode.ExchangeCodeRequest;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.ExRecordStatusEnum;
import com.mem.vo.common.constant.ExchangeCodeBizType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.util.DateUtil;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.QRCodeUtil;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author litongwei
 * @description 兑换码controller
 * @date 2019/6/27 13:04
 */

@RestController
@RequestMapping("/exchange-code")
@Slf4j
public class ExchangeCodeController {

    @Resource
    private ExchangeCodeMainService exchangeCodeMainService;

    @Resource
    private ExchangeCodeRecordService exchangeCodeRecordService;

    @Resource
    private ShowSeatsService showSeatsService;


    @Resource
    private TokenService tokenService;

    @Resource
    private CouponService couponService;

    @Resource
    private OrderService orderService;

    @Resource
    private PerformanceService performanceService;

    @Resource
    private PerformanceShowService performanceShowService;

    @Resource
    private ExchangeCodeMainDao exchangeCodeMainDao;

    @Resource
    private AreaService areaService;

    @Resource
    private UserService userService;

    @Resource
    private ExchangeLogService exchangeLogService;

    @Resource
    private TicketStoreService ticketStoreService;

    @Resource
    private TicketGearService ticketGearService;

    @PostMapping("/queryList")
    @CommonExHandler(key = "兑换码查询")
    ResponseDto<JSONObject> queryList(@RequestHeader("token") String token, Page page,
                                      ExchangeCodeMainQuery query){
        JSONObject jsonObject = new JSONObject();
        BizAssert.notNull(query,"查询条件为空,参数错误");
        BizAssert.notNull(query.getBusinessTag(),"业务来源标为空，参数错误");
        Page<ExchangeCodeMain> resPage = exchangeCodeMainService.findPageByCondition(page, query);
        if(resPage!=null){
            jsonObject.put("total",resPage.getTotal());
            if(query.getBusinessTag().equals(ExchangeCodeBizType.FOR_COUPON.getCode())){
                //补充优惠券信息
                if(CollectionUtils.isNotEmpty(resPage.getData())){
                    for (ExchangeCodeMain exchangeCodeMain:resPage.getData()){
                        String couponId = exchangeCodeMain.getBusinessKey();
                        BizAssert.hasText(couponId,"优惠券对应的业务 id 为空");
                        Coupon coupon = couponService.findById(Long.valueOf(couponId));
                        BizAssert.notNull(coupon,"查询对应优惠券为空");
                        exchangeCodeMain.setCoupon(coupon);
                    }
                }
            }

            jsonObject.put("list",resPage.getData());

        }
        return new ResponseDto().successData(resPage);
    }

    @PostMapping("/generateCommonExchangeCode")
    @CommonExHandler(key = "通用兑换码生成")
    @Transactional
    ResponseDto<Boolean> generateCommonExchangeCode(@RequestHeader("token") String token, ExchangeCodeRequest exchangeCodeRequest){

        CommonLoginContext context = tokenService.getContextByken(token);
        BizAssert.notNull(context,"登陆信息不存在");
        BizAssert.notNull(context.getUserId(),"用户id获取为空");

        List<String>  list = exchangeCodeMainService.generateCommonExchangeCode(context.getUserId(),exchangeCodeRequest);
        BizAssert.notEmpty(list,"创建兑换码为空");
        return new ResponseDto().successData(true);
    }


    @PostMapping("/updateEnableStatus")
    @CommonExHandler(key = "兑换码启用停用")
    ResponseDto<Boolean> updateEnableStatus(@RequestHeader("token") String token,
                                      ExchangeCodeMain main){
        JSONObject jsonObject = new JSONObject();
        BizAssert.notNull(main,"查询条件为空,参数错误");
        //BizAssert.notNull(query.getBusinessTag(),"业务来源标为空，参数错误");
        BizAssert.notNull(main.getId(),"id 为空，参数错误");
        BizAssert.notNull(main.getStatus(),"兑换码状态");
        CommonLoginContext context = tokenService.getContextByken(token);
        BizAssert.notNull(context,"获取登陆信息为空");
        BizAssert.notNull(context.getUserId(),"获取用户id为空");
        main.setUpdateUser(context.getUserId().toString());
        exchangeCodeMainService.updateById(main);
        return new ResponseDto().successData(true);
    }



    @PostMapping("/queryRecordList")
    @CommonExHandler(key = "查询兑换码使用情况")
    ResponseDto<Page<ExchangeCodeRecord>> queryRecordList(@RequestHeader("token") String token,
                                                Page page,ExchangeCodeRecordQuery query){

        BizAssert.notNull(query,"查询参数为空");
        BizAssert.notNull(query.getMainId(),"主表id为空，参数错误");

        Page<ExchangeCodeRecord> resPage = exchangeCodeRecordService.findPageByCondition(page,query);
        return new ResponseDto().successData(resPage);
    }

    @PostMapping("/queryTiekctInfo")
    @CommonExHandler(key = "查询票品信息")
    @Transactional
    ResponseDto<JSONObject> queryTiekctInfo(@RequestHeader("token") String token,CheckTicketVo checkTicketVo){

        BizAssert.notNull(checkTicketVo.getPerformanceId(),"演出 id 为空");
        CommonLoginContext context = tokenService.getContextByken(token);
        BizAssert.notNull(context.getUserId(),"用户 id为空");

        JSONObject jsonObject = new JSONObject();
        BizAssert.hasText(checkTicketVo.getExchangeCode(),"兑换码为空,参数错误");
        ExchangeCodeRecordQuery query = new ExchangeCodeRecordQuery();
        query.setIsDelete(0);
        query.setExchangeCode(checkTicketVo.getExchangeCode());
        List<ExchangeCodeRecord> list = exchangeCodeRecordService.findByCondition(query);
        BizAssert.notEmpty(list,"兑换码不存在");
        ExchangeCodeRecord record = list.get(0);

        if(ExchangeCodeBizType.FOR_TICKET.getCode().equals(record.getBusinessTag())){
            //电子票
            //用户名 手机号  演出名称  场次   张数

            Order order = getOrder(record);
            Long perfromanceId = order.getPerformanceId();
            Performance performance = performanceService.findById(perfromanceId);
            BizAssert.notNull(performance,"演出为空");
            //BizAssert.isTrue(checkTicketVo.getPerformanceId().equals(performance.getId()),"演出不匹配");
            jsonObject.put("performanceName",performance.getTitle());

            Long showId = order.getShowId();
            PerformanceShow performanceShow = performanceShowService.findById(showId);

            BizAssert.notNull(performanceShow,"场次为空，数据异常");
            jsonObject.put("ShowName",performanceShow.getShowName());
            jsonObject.put("ticketNumer",order.getTicketNum());
            User user = userService.findById(order.getUserId());
            BizAssert.notNull(user,"用户不存在,id："+ order.getUserId());
            jsonObject.put("userName",user.getName());
            jsonObject.put("phoneNumber",user.getPhoneNumber());
            jsonObject.put("showStartTIme",performanceShow.getStartTime());
            jsonObject.put("showEndTIme",performanceShow.getEndTime());
            if(!checkTicketVo.getPerformanceId().equals(performance.getId())){
                jsonObject.put("errMsg","持有兑换码不是当前的演出，无法验票");
                return new ResponseDto().successData(jsonObject);
            }

            if(!DateUtil.formatDateStr(new Date()).equals(DateUtil.formatDateStr(performanceShow.getStartTime()))){
                jsonObject.put("errMsg","不是今天的场次，请核对信息");
                return new ResponseDto().successData(jsonObject);


            }

            jsonObject.put("errMsg","");



        }else if(ExchangeCodeBizType.FOR_COUPON.getCode().equals(record.getBusinessTag())){
            //优惠券

        }else{
            //奖品
        }

        return new ResponseDto().successData(jsonObject);

    }

    private Order getOrder(ExchangeCodeRecord record) {
        String orderNumber = record.getBusinessKey();
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.setOrderNumber(orderNumber);
        List<Order> orders = orderService.findByCondition(orderQuery);
        BizAssert.notEmpty(orders,"查询订单为空");
        return (Order) orders.get(0);
    }


    @PostMapping("/handCheckTicket")
    @CommonExHandler(key = "手动验票")
    @Transactional
    //后期增加 token
    ResponseDto<Boolean> handCheckTicket(@RequestHeader("token") String token, CheckTicketVo checkTicketVo){

        CommonLoginContext context = tokenService.getContextByken(token);
        BizAssert.hasText(checkTicketVo.getExchangeCode(),"兑换码为空，参数错误");

        ExchangeCodeRecordQuery exchangeCodeRecordQuery=new ExchangeCodeRecordQuery();
        exchangeCodeRecordQuery.setExchangeCode(checkTicketVo.getExchangeCode());
        List<ExchangeCodeRecord> exchangeCodeRecords = exchangeCodeRecordService.findByCondition(exchangeCodeRecordQuery);
        BizAssert.notEmpty(exchangeCodeRecords,"兑换码记录为空");
        ExchangeCodeRecord exchangeCodeRecord =   exchangeCodeRecords.get(0);


        //根据当前状态判断更新
        ExchangeCodeRecordQuery query  = new ExchangeCodeRecordQuery();
        query.setExchangeCode(checkTicketVo.getExchangeCode());
        List<ExchangeCodeRecord> records = exchangeCodeRecordService.findByCondition(query);
        BizAssert.notEmpty(records,"验票记录为空");
        ExchangeCodeRecord record = records.get(0);

        String oldStatus = "";
        String status  = "";

        if(ExchangeCodeBizType.FOR_TICKET.getCode().equals(record.getBusinessTag())){
            //电子票
            BizAssert.isTrue(! ExRecordStatusEnum.USED.getCode().equals(exchangeCodeRecord.getStatus()),"票已经使用，无法兑换");
            oldStatus =  ExRecordStatusEnum.UN_USE.getCode();
            status = ExRecordStatusEnum.USED.getCode();
        }else if(ExchangeCodeBizType.FOR_COUPON.getCode().equals(record.getBusinessTag())){
            //优惠券

        }else{
            //奖品
        }
        int count =  exchangeCodeRecordService.updateByExchangeCode(checkTicketVo.getExchangeCode(),oldStatus,status);
        BizAssert.isTrue(count>0,"兑换码状态更新失败");
        //更新主表,



        if(ExchangeCodeBizType.FOR_TICKET.getCode().equals(record.getBusinessTag())){
            //电子票
            //主表修改兑换数量
            exchangeCodeMainDao.updateUsedNumById(exchangeCodeRecord.getMainId());
        }else if(ExchangeCodeBizType.FOR_COUPON.getCode().equals(record.getBusinessTag())){
            //优惠券

        }else{
            //奖品
        }

        ExchangeLog exchangeLog= new ExchangeLog();
        exchangeLog.setPerformanceId(checkTicketVo.getPerformanceId());
        Performance performance = performanceService.findById(checkTicketVo.getPerformanceId());
        BizAssert.notNull(performance,"演出为空");
        exchangeLog.setPerformanceName(performance.getTitle());
        exchangeLog.setOperatorId(context.getUserId());
        exchangeLog.setOperatorPhoneNumber(context.getUser().getPhoneNumber());
        exchangeLog.setOperatorName(context.getUser().getName());
        exchangeLog.setUserId(checkTicketVo.getUserId());
        User user = userService.findById(checkTicketVo.getUserId());
        BizAssert.notNull(user,BizCode.BIZ_1009.getMessage());
        exchangeLog.setUserPhoneNumber(user.getPhoneNumber());
        exchangeLog.setUserName(user.getPhoneNumber());
        exchangeLog.setExchangeNum(getOrder(record).getTicketNum());
        exchangeLog.setExchangeCode(checkTicketVo.getExchangeCode());

        exchangeLogService.insert(exchangeLog);

        return new ResponseDto().successData(true);
    }

    @PostMapping("/queryRecord")
    @CommonExHandler(key = "查询验票员兑换记录")
    public ResponseDto<Page<ExchangeLog>> queryCheckLog(@RequestHeader("token") String token,Page page,ExchangeLogQuery query){
        Page<ExchangeLog> resPage = exchangeLogService.findPageByCondition(page,query);
        return new ResponseDto<>().successData(resPage);
    }


    @PostMapping("/generateTicketQrCode")
    @CommonExHandler(key = "生成验二维码")
    public ResponseDto<String> generateTicketQrCode(@RequestHeader("token") String token, CheckTicketVo checkTicketVo) throws Exception {
        BizAssert.notNull(checkTicketVo,"参数为空");
        BizAssert.notNull(checkTicketVo.getPerformanceId(),"演出 id 为空");
        BizAssert.hasText(checkTicketVo.getExchangeCode(),"兑换码为空");
        CommonLoginContext context = tokenService.getContextByken(token);
        checkTicketVo.setUserId(context.getUserId());
        String base64Code = QRCodeUtil.encodeBase64(JsonUtil.toJson(checkTicketVo),null,true);
        return new ResponseDto().successData(base64Code);

    }



    @PostMapping("/generate/ticketStore/exchangeCode")
    @CommonExHandler(key = "生成库存兑换码")
    public ResponseDto<Boolean> generateTicketGearExchangeCode(@RequestHeader("token") String token,
        Long  ticketStoreId,Integer number) throws Exception {

        CommonLoginContext context = tokenService.getContextByken(token);
        BizAssert.notNull(context,"登陆信息不存在");
        BizAssert.notNull(context.getUserId(),"用户id获取为空");

        BizAssert.notNull(ticketStoreId,"库存id为空，参数错误");
        BizAssert.notNull(number,"数量为空，参数错误");

        TicketStore ticketStore = ticketStoreService.findById(ticketStoreId);
        BizAssert.notNull(ticketStore,"根据 id 查询库存不存在");
        BizAssert.isTrue(ticketStore.getStoreNum()-number>=0,"扣减后库存为负数，无法处理");
        ticketStore.setStoreNum(ticketStore.getStoreNum()-number);
        ticketStoreService.updateById(ticketStore);


        ExchangeCodeRequest exchangeCodeRequest = new ExchangeCodeRequest();
        exchangeCodeRequest.setBusinessKey(String.valueOf(ticketStoreId));
        exchangeCodeRequest.setBusinessTag(ExchangeCodeBizType.FOR_NOSEAT_TICKET.getCode());
        exchangeCodeRequest.setNumber(number);
        List<String> exchangeCodeList = new ArrayList<>();
        for(int i=0;i< number;i++){
            exchangeCodeList.add(exchangeCodeRequest.getBusinessKey());
        }
        exchangeCodeRequest.setRecordBusinessKeyList(exchangeCodeList);


        List<String>  list = exchangeCodeMainService.generateCommonExchangeCode(context.getUserId(),exchangeCodeRequest);
        BizAssert.notEmpty(list,"创建兑换码为空");

        return new ResponseDto().successData(true);

    }


    @PostMapping("/queryExchangeCodeForSms")
    @CommonExHandler(key = "查询赞助商查询兑换码")
    public ResponseDto<Page<ExRecordVo>> queryExchangeCodeForSms (@RequestHeader("token") String token,String  exchangeCode,
        Page page,Integer  type,Integer status) throws Exception {
        ExchangeCodeRecordQuery query = new ExchangeCodeRecordQuery();
        query.setExchangeCode(exchangeCode);
        //返回 演出  场次  business-key   票档/座位
        if(ExchangeCodeBizType.FOR_SEAT_TICKET.getCode().equals(type) || ExchangeCodeBizType.FOR_NOSEAT_TICKET.getCode().equals(type)){
            query.setBusinessTag(type);
        }

        if(null != status && status!=0){
            query.setStatus(String.valueOf(status));
        }
        Page<ExchangeCodeRecord> exchangeCodeRecords = exchangeCodeRecordService.findPageByCondition(page,query);
        Page<ExRecordVo> resPage = new Page<>();
        resPage.setTotal(exchangeCodeRecords.getTotal());
        List<ExRecordVo> list = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(exchangeCodeRecords.getData())){
            for(ExchangeCodeRecord exchangeCodeRecord:exchangeCodeRecords.getData()){

                BizAssert.hasText(exchangeCodeRecord.getBusinessKey(),"查询businessKey 为空");
                ExRecordVo exRecordVo = new ExRecordVo();
                exRecordVo.setBusinessTag(exchangeCodeRecord.getBusinessTag());
                exRecordVo.setExchangeCode(exchangeCodeRecord.getExchangeCode());

                if(ExchangeCodeBizType.FOR_SEAT_TICKET.getCode().equals(exchangeCodeRecord.getBusinessTag())){

                    //有座 business 座位 id
                    ShowSeats curss= showSeatsService.findById(Long.valueOf(exchangeCodeRecord.getBusinessKey()));

                    BizAssert.notNull(curss,"查询演出座位为空");
                    exRecordVo.setPerformanceId(curss.getPerformanceId());
                    exRecordVo.setShowId(curss.getShowId());
                    exRecordVo.setAreaId(curss.getAreaId());


                    list.add(exRecordVo);

                }else{

                    //无座  businesskey 是库存  id

                    TicketStore ticketStore = ticketStoreService.findById(Long.valueOf(exchangeCodeRecord.getBusinessKey()));
                    BizAssert.notNull(ticketStore,"查询库存为空");

                    exRecordVo.setPerformanceId(ticketStore.getPerformanceId());
                    exRecordVo.setShowId(ticketStore.getShowId());
                    exRecordVo.setTicketGearId(ticketStore.getTicketGearId());


                }

                if(exRecordVo.getPerformanceId()!=null){

                    Performance performance = performanceService.findById(exRecordVo.getPerformanceId());

                    BizAssert.notNull(performance,"查询演出为空");
                    exRecordVo.setPerformanceName(performance.getTitle());
                }

                if(exRecordVo.getShowId()!=null){

                    PerformanceShow performanceShow = performanceShowService.findById(exRecordVo.getShowId());
                    BizAssert.notNull(performanceShow,"查询场次为空");

                    exRecordVo.setShowName(performanceShow.getShowName());
                }


                if(exRecordVo.getAreaId()!=null){

                    Area area = areaService.findById(exRecordVo.getAreaId());
                    BizAssert.notNull(area,"区域为空");
                    exRecordVo.setAreaName(area.getAreaName());
                }

                if(exRecordVo.getTicketGearId()!=null){
                    TicketGear ticketGear = ticketGearService.findById(exRecordVo.getTicketGearId());
                    BizAssert.notNull(ticketGear,"票档为空");
                    exRecordVo.setTicketGearName(ticketGear.getTicketGearName());
                }

                exRecordVo.setStatus(exchangeCodeRecord.getStatus());

                exRecordVo.setStatuaName(ExRecordStatusEnum.getNameByCode(exRecordVo.getStatus()));

                list.add(exRecordVo);


            }
            resPage.setData(list);

        }
        return new ResponseDto<>().successData(resPage);

    }


}
