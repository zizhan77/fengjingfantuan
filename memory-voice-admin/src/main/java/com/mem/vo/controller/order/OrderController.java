package com.mem.vo.controller.order;

import static com.mem.vo.common.util.WxSignUtil.encodeByMD5;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.Order;
import com.mem.vo.business.base.model.po.OrderQuery;
import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.PerformanceQuery;
import com.mem.vo.business.base.model.po.ShowSeats;
import com.mem.vo.business.base.model.po.TicketGear;
import com.mem.vo.business.base.model.po.TicketGearQuery;
import com.mem.vo.business.base.model.po.TicketStore;
import com.mem.vo.business.base.model.po.TicketStoreQuery;
import com.mem.vo.business.base.model.po.TicketStoreRecord;
import com.mem.vo.business.base.model.po.User;
import com.mem.vo.business.base.model.po.UserAddress;
import com.mem.vo.business.base.model.po.UserOperate;
import com.mem.vo.business.base.service.ExchangeCodeMainService;
import com.mem.vo.business.base.service.OrderService;
import com.mem.vo.business.base.service.PerformanceService;
import com.mem.vo.business.base.service.ShowSeatsService;
import com.mem.vo.business.base.service.TicketGearService;
import com.mem.vo.business.base.service.TicketStoreRecordService;
import com.mem.vo.business.base.service.TicketStoreService;
import com.mem.vo.business.base.service.UserAddressService;
import com.mem.vo.business.base.service.UserOperateService;
import com.mem.vo.business.base.service.UserService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.dto.WxOrderContext;
import com.mem.vo.business.biz.model.dto.WxOrderRequest;
import com.mem.vo.business.biz.model.dto.WxOrderResign;
import com.mem.vo.business.biz.model.vo.OrderVo;
import com.mem.vo.business.biz.model.vo.SeatsIdsNumVo;
import com.mem.vo.business.biz.model.vo.exchangecode.ExchangeCodeRequest;
import com.mem.vo.business.biz.service.login.WxLoginService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.ExchangeCodeBizType;
import com.mem.vo.common.constant.OrderStatusEnum;
import com.mem.vo.common.constant.SeatStatus;
import com.mem.vo.common.constant.TicketDeliverType;
import com.mem.vo.common.constant.UserOperateEnum;
import com.mem.vo.common.constant.WxConstant;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.*;
import com.mem.vo.config.annotations.CommonExHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baiyuehui
 * @description: 订单
 * @date 2019/5/25 15:43
 */

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private TicketGearService ticketGearService;
    @Resource
    private TicketStoreService ticketStoreService;
    @Resource
    private TicketStoreRecordService ticketStoreRecordService;
    @Resource
    private ShowSeatsService showSeatsService;
    @Resource
    private PerformanceService performanceService;
    @Resource
    private WxLoginService wxLoginService;
    @Resource
    private TokenService tokenService;
    @Resource
    private ExchangeCodeMainService exchangeCodeMainService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private WxOrderRequest wxOrderRequest;
    @Resource
    private UserOperateService userOperateService;
    @Resource
    private UserService userService;


    @PostMapping("/queryOrderList")
    public ResponseDto<List<Order>> queryOrderList(@RequestHeader("token") String token, OrderQuery orderQuery) {

        //权限验证
        ResponseDto<List<Order>> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(orderQuery.getUserId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(orderService.findByCondition(orderQuery));

        } catch (BizException e) {

            log.error("获取订单列表异常，参数:{},原因：{}", orderQuery.getUserId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取订单列表异常，参数:{}", orderQuery.getUserId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/getOrderListByPage")
    public ResponseDto<JSONObject> getOrderListByPage(
            OrderQuery orderQuery) {
        //分页查询代码冗余，可优化
        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();

        try {
            JSONObject jsonObject = new JSONObject();
            Page page = new Page();
            page.setPage(orderQuery.getPage());
            page.setLimit(orderQuery.getLimit());

            Page<Order> resPage = orderService.findPageByCondition(page, orderQuery);
            List<Order> resList = resPage.getData();
            List<OrderVo> realResList = new LinkedList<>();
            for (Order o : resList) {
                Performance p = performanceService.findById(o.getPerformanceId());
                OrderVo ovo = BeanCopyUtil.copyProperties(o, OrderVo.class);
                ovo.setTicketDeliverType(TicketDeliverType.getNameByCode(p.getTicketDeliverType()));
                ovo.setTitle(p.getTitle());
                realResList.add(ovo);
            }
            jsonObject.put("total", resPage.getTotal());
            jsonObject.put("list", realResList);

            return responseDto.successData(jsonObject);

        } catch (BizException e) {

            log.error("分页获取订单列表异常,参数1：{}，参数2：{} , 原因：{}", JsonUtil.toJson(orderQuery), JsonUtil.toJson(""),
                    e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("分页获取订单列表异常，参数1：{}，参数2：{}", JsonUtil.toJson(orderQuery), JsonUtil.toJson(""), e);
            return responseDto.failSys();
        }

    }

    //修改一个订单的后台信息@param orderId
    @PostMapping("/editOrderBillCode")
    public ResponseDto<String> editOrderBillCode(@RequestHeader("token") String token, Order order) {

        //权限验证
        ResponseDto<String> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(order.getWaybillCode(), BizCode.PARAM_NULL.getMessage());
            Order o = orderService.findById(order.getId());
            Performance p = performanceService.findById(o.getPerformanceId());
//            if (p.getTicketDeliverType().equals(TicketDeliverType.Express.getCode())) {
//                if (o.getStatus() == OrderStatusEnum.WAITSEND.getCode()
//                        || o.getStatus() == OrderStatusEnum.SENDOUT.getCode()) {
//                    //如果为电子码，存个json压压惊
//                    o.setWaybillCode(order.getWaybillCode());
//                    o.setStatus(OrderStatusEnum.SENDOUT.getCode());
//                }
//            } else {
//                //电子票直接就是已完成状态
//            }
            if (p.getTicketDeliverType().equals(TicketDeliverType.Express.getCode()) && (
                    o.getStatus().equals(OrderStatusEnum.WAITSEND.getCode())
                            || o.getStatus().equals(OrderStatusEnum.SENDOUT.getCode()))) {

                o.setWaybillCode(order.getWaybillCode());
                o.setStatus(OrderStatusEnum.SENDOUT.getCode());
            }
            orderService.updateById(o);
            return responseDto.successData("保存订单信息成功");

        } catch (BizException e) {

            log.error("保存订单信息异常，参数:{},原因：{}", order.getWaybillCode(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("保存订单信息异常，参数:{}", order.getWaybillCode(), e);
            return responseDto.failSys();
        }
    }


    //此处需要优化，加钱
    @PostMapping("/createOrder")
    @Transactional
    @CommonExHandler(key = "创建订单")
    public ResponseDto<WxOrderResign> createOrder(@RequestHeader("token") String token, OrderVo order) throws Exception {

        ResponseDto<WxOrderResign> responseDto = ResponseDto.successDto();
        //TODO
        //优惠券业务 undone
        log.error(order.toString());

        //获取用户id
        CommonLoginContext context = tokenService.getContextByken(token);
        order.setUserId(context.getUserId());
        BizAssert.notNull(order.getUserId(), BizCode.PARAM_NULL.getMessage());

        //解析json

        List<SeatsIdsNumVo> seatsIdsNumVoList = JSONArray.parseArray(order.getSeatsIdsNumVoList(), SeatsIdsNumVo.class);
        //创建订单
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR));
        sb.append(Calendar.getInstance().getTimeInMillis());
        sb.append((int) Math.random() * 10);
        order.setOrderNumber(sb.toString());
        order.setStatus(OrderStatusEnum.WAITPAY.getCode());
        Order orderPo = BeanCopyUtil.copyProperties(order, Order.class);
        orderPo.setCreateTime(new Date());


        //预占库存、座位
        PerformanceQuery p = new PerformanceQuery();
        Performance performance = performanceService.findById(order.getPerformanceId());
        List<TicketStore> ticketStoreList = new ArrayList<>();
        List<TicketGear> ticketGearList = new ArrayList<>();
        order.setTicketDeliverType(performance.getTicketDeliverType()+"");
        //历史库存查询
        if (order.getTicketGearId() != null) {
            //非选座
            TicketStoreQuery t = new TicketStoreQuery();
            t.setShowId(order.getShowId());
            t.setTicketGearId(order.getTicketGearId());
            ticketStoreList = ticketStoreService.findByCondition(t);
            TicketGearQuery ticketGearQuery = new TicketGearQuery();
            ticketGearQuery.setId(order.getTicketGearId());
            ticketGearList = ticketGearService.findByCondition(ticketGearQuery);
        } else {
            //选座
            String[] gearIds = performance.getTicketGearIds().split("~");
            if (gearIds.length <= 0) {
                throw new BizException(BizCode.PARAM_NULL.getMessage());
            }
            for (String s : gearIds) {
                TicketStoreQuery t = new TicketStoreQuery();
                t.setShowId(order.getShowId());
                t.setTicketGearId(Long.parseLong(s));
                ticketStoreList.addAll(ticketStoreService.findByCondition(t));
                TicketGearQuery ticketGearQuery = new TicketGearQuery();
                ticketGearQuery.setId(Long.parseLong(s));
                ticketGearList.addAll(ticketGearService.findByCondition(ticketGearQuery));
            }
        }
        Map<String, String> atRelationMap = new HashMap<>();
        Map<String, Integer> anRelationMap = new HashMap<>();
        Map<String, BigDecimal> tpRelationMap = new HashMap<>();
        if (order.getTicketGearId() == null) {
            //字典生成
            //area-ticketGear
            for (TicketGear t : ticketGearList) {
                atRelationMap.put(t.getAreaId() + "", t.getId() + "");
                tpRelationMap.put(t.getId() + "", t.getPrice());
            }
            //seat-num
            for (SeatsIdsNumVo vo : seatsIdsNumVoList) {
                if (anRelationMap.get(vo.getAreaId()) == null) {
                    anRelationMap.put(vo.getAreaId(), 1);
                    continue;
                }
//                else {
                    anRelationMap.put(vo.getAreaId(), anRelationMap.get(vo.getAreaId()) + 1);
//                }
            }
        }
        if (ticketStoreList.size() <= 0) {
            throw new BizException(BizCode.BIZ_1033.getMessage());
        } else {
            if (order.getTicketGearId() != null) {
                //非选座预占库存
                TicketStore ticketStore = ticketStoreList.get(0);
                if (ticketStore.getSaleNum() + order.getTicketNum() > ticketStore.getStoreNum()) {
                    throw new BizException(BizCode.BIZ_1036.getMessage());
                }
                ticketStore.setSaleNum(ticketStore.getSaleNum() + order.getTicketNum());
                ticketStoreService.updateById(ticketStore);
            } else {
                //选座预占库存
                String curGear = null;
                for (SeatsIdsNumVo vo : seatsIdsNumVoList) {
                    String newGear = atRelationMap.get(vo.getAreaId());
                    if (newGear.equals(curGear)) {
                        continue;
                    }
//                    else {
                        curGear = newGear;
//                    }
                    for (TicketStore ticketStore : ticketStoreList) {
                        if (curGear.equals(ticketStore.getTicketGearId() + "")) {
                            if (ticketStore.getSaleNum() + anRelationMap.get(vo.getAreaId()) > ticketStore.getStoreNum()) {
                                throw new BizException(BizCode.BIZ_1036.getMessage());
                            }
                            ticketStore.setSaleNum(ticketStore.getSaleNum() + anRelationMap.get(vo.getAreaId()));
                            ticketStoreService.updateById(ticketStore);
                        }
                    }
                }

            }
        }

        if (order.getTicketGearId() != null) {
            //非选座插入库存流水
            TicketStoreRecord ticketStoreRecord = new TicketStoreRecord();
            //实际为orderNumber
            ticketStoreRecord.setOrderId(Long.parseLong(order.getOrderNumber()));
            ticketStoreRecord.setTicketGearId(order.getTicketGearId());
            ticketStoreRecord.setOccupyNum(order.getTicketNum());
            ticketStoreRecord.setPayStatus(0);//代表预占
            ticketStoreRecordService.insert(ticketStoreRecord);
        } else {
            if (order.getSeatsIdsNumVoList() != null) {
                StringBuilder seatsid = new StringBuilder();
                //String preArea = null;
                for (Map.Entry<String, Integer> entry : anRelationMap.entrySet()) {
                    TicketStoreRecord ticketStoreRecord = new TicketStoreRecord();
                    //实际为orderNumber
                    ticketStoreRecord.setOrderId(Long.parseLong(order.getOrderNumber()));
                    String curArea = entry.getKey();
                    //更换areaId就插入一次
                    List<SeatsIdsNumVo> seatsList = seatsIdsNumVoList
                            .stream().filter(item -> item.getAreaId().equals(curArea))
                            .collect(Collectors.toList());

                    for (SeatsIdsNumVo s : seatsList) {
                        seatsid.append("," + s.getId());
                        ShowSeats showSeats = new ShowSeats();
                        showSeats.setId(Long.parseLong(s.getId()));
                        showSeats.setStatus(SeatStatus.SELLOUT.getCode());
                        if (showSeatsService.saveSeat(showSeats) < 1) {
                            throw new BizException(BizCode.BIZ_1034.getMessage());
                        }
                    }
                    if(orderPo.getSeatIds()==null){
                        seatsid.deleteCharAt(0);
                        orderPo.setSeatIds(seatsid.toString());
                    }else{
                        orderPo.setSeatIds(orderPo.getSeatIds()+seatsid.toString());
                    }
                    seatsid.deleteCharAt(0);
                    ticketStoreRecord.setSeatIds(seatsid.toString());
                    ticketStoreRecord.setTicketGearId(Long.parseLong(atRelationMap.get(entry.getKey())));
                    ticketStoreRecord.setOccupyNum(entry.getValue());
                    ticketStoreRecord.setPayStatus(0);//代表预占
                    ticketStoreRecordService.insert(ticketStoreRecord);
                    seatsid = new StringBuilder();
                }
            }
        }
        //创建支付失败计时器
        ticketStoreRecordService.createTimerForTicketRecord(order.getOrderNumber());
        BigDecimal totalPrice = new BigDecimal(0);
        //计算订单金额
        if (order.getTicketGearId() != null) {
            //非选座计算金额,只有一种票档

            BigDecimal ticketPrice = ticketGearList.get(0).getPrice().multiply(new BigDecimal(order.getTicketNum()));
            totalPrice = totalPrice.add(ticketPrice);
            //log.error("非选座计算金额,只有一种票档"+ticketPrice);
        } else {
            //选座计算金额
            for (Map.Entry<String, Integer> entry : anRelationMap.entrySet()) {
                BigDecimal ticketPrice =
                        tpRelationMap.get(atRelationMap.get(entry.getKey())).multiply(new BigDecimal(entry.getValue()));
                totalPrice = totalPrice.add(ticketPrice);
            }
        }
        //取运费暂时写死
        if (order.getUserAddressId() != null) {
            UserAddress addressDetail = userAddressService.findById(order.getUserAddressId());
            orderPo.setDeliverAddress(addressDetail.getDestOneAddress()
                    + " " + addressDetail.getDestTwoAddress()
                    + " " + addressDetail.getDestThreeAddress()
                    + " " + addressDetail.getDestFourAddress()
                    + " " + addressDetail.getAddress());
        }
        //totalPrice = order.getPrice();
        //取地址待修改，电子票没运费
        if(order.getTicketDeliverType().equals(TicketDeliverType.Express.getCode())){
            orderPo.setFreight(new BigDecimal(10));
            totalPrice.add(orderPo.getFreight());
        }
        log.error("totalPrice"+totalPrice);
        orderPo.setPrice(totalPrice);
        int orderId = orderService.insert(orderPo);

        //写一下操作记录
        UserOperate userOperate = new UserOperate();
        userOperate.setUserId(context.getUserId());
        userOperate.setPhoneNumber(context.getUser().getPhoneNumber());
        userOperate.setType(UserOperateEnum.CREATE_ORDER.getCode());
        try {

            userOperateService.insert(userOperate);
        } catch (Exception e) {
            log.error("创建订单写操作日志失败：用户id :{}", context.getUserId(), e);
        }

        //responseDto.successData(new WxOrderResign());
        //微信统一支付接口,order.getPrice()可临时使用
        try {
            responseDto.successData(wxPayOrder(orderPo,context.getBizCode()));
        } catch (Exception e) {
            throw e;
        }
        return responseDto;
    }

    public ResponseDto<String> wxPaySucess(String orderNumber) {

//        订单状态
//        待支付，后台无操作；
//        待发货，备票发货(录入快递单号，确定后订单变成已发货状态),
//        取消订单(订单状态变成已取消)；
//        已发货，可修改快递单号；
//        已完成，如票品为电子票，可查看电子码、重置电子码；
        ResponseDto<String> responseDto = ResponseDto.successDto();

        try {
            BizAssert.notNull(orderNumber, BizCode.PARAM_NULL.getMessage());
            OrderQuery o = new OrderQuery();
            o.setOrderNumber(orderNumber);
            List<Order> orders = orderService.findByCondition(o);
            if (orders == null) {
                throw new BizException(BizCode.BIZ_1050.getMessage());
            }

            Order curOrder = orders.get(0);
            log.error("当前order"+curOrder.toString());
            if (curOrder.getStatus() > 0) {
                throw new BizException(BizCode.BIZ_1051.getMessage());
            }

            Performance p = performanceService.findById(curOrder.getPerformanceId());
            if (p.getTicketDeliverType().equals(TicketDeliverType.Express.getCode())) {
                //快递
                curOrder.setStatus(OrderStatusEnum.WAITSEND.getCode());
            } else {
                //电子票
                List<String> recordKey = new ArrayList<>();
                recordKey.add(orderNumber);
                ExchangeCodeRequest exchangeCodeRequest = ExchangeCodeRequest.builder()
                        .businessKey(orderNumber)
                        .recordBusinessKeyList(recordKey)
                        .businessTag(ExchangeCodeBizType.FOR_TICKET.getCode())
                        .number(1).build();

                List<String> exCodeList = exchangeCodeMainService.generateCommonExchangeCode(curOrder.getUserId(), exchangeCodeRequest);
                curOrder.setWaybillCode(exCodeList.get(0));
                curOrder.setStatus(OrderStatusEnum.FINISHED.getCode());
            }
            orderService.updateById(curOrder);

            try {
                UserOperate userOperate = new UserOperate();
                BizAssert.notNull(curOrder.getUserId(), "用户id不存在");
                userOperate.setUserId(curOrder.getUserId());
                User user = userService.findById(curOrder.getUserId());
                BizAssert.notNull(user, "查询用户不存在：id:" + curOrder.getUserId());
                userOperate.setPhoneNumber(user.getPhoneNumber());
                userOperate.setType(UserOperateEnum.PAY.getCode());
                userOperateService.insert(userOperate);
            } catch (Exception e) {
                log.error("订单支付写操作日志失败：用户id :{}", curOrder.getUserId(), e);
            }
            //判断票品信息
            return responseDto.successData("1");
        } catch (BizException e) {
            log.error("订单支付异常，参数:{},原因：{}", orderNumber, e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("订单支付异常，参数:{}", orderNumber, e);
            return responseDto.failSys();
        }
    }
    @PostMapping("/wxPayOrderAgain")
    public ResponseDto<WxOrderResign> wxPayOrderAgain(@RequestHeader("token") String token,String orderNumber) throws Exception {
        //统一下单	都需要先获取到Openid，调用相同的API
        log.error(wxOrderRequest.toString());
        ResponseDto<WxOrderResign> responseDto = ResponseDto.successDto();

        CommonLoginContext context = tokenService.getContextByken(token);
        BizAssert.notNull(context, BizCode.PARAM_NULL.getMessage());
        OrderQuery o = new OrderQuery();
        o.setOrderNumber(orderNumber);
        List<Order> orders = orderService.findByCondition(o);
        if (orders == null) {
            throw new BizException(BizCode.BIZ_1050.getMessage());
        }
        Order curOrder = orders.get(0);
        WxOrderRequest realRequest = new WxOrderRequest();
        realRequest = BeanCopyUtil.copyProperties(wxOrderRequest, WxOrderRequest.class);
        realRequest.setOpenid(context.getBizCode());
        realRequest.setOut_trade_no(orderNumber);
        realRequest.setTotal_fee(curOrder.getPrice().multiply(new BigDecimal("100")).intValue());
        realRequest.setNonce_str(wxOrderRequest.getNonce_str());
        realRequest.setShopKey(wxOrderRequest.getShopKey());
        realRequest.setA_package(null);
        realRequest.setBody("记忆之声-购票");
        //已初始化
        realRequest.setNotify_url("http://47.92.115.105/order/receiveNotify");
        Map<String, Object> map = PayUtil.sendPayment(realRequest);
        realRequest.setSign(map.get("sign").toString());
        log.error("realRequest" + realRequest.toString());
        realRequest.setTimeStamp(null);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            log.error("map kv"+map.get(entry.getKey())+entry.getValue());
        }
        WxOrderContext wxOrderContext = WxOrderContext.builder()
                .return_code(map.get("return_code").toString())
                .return_msg(map.get("return_msg").toString())
                .result_code(map.get("result_code").toString())
                .trade_type(map.get("trade_type").toString())
                .prepay_id(map.get("prepay_id").toString())
                .build();
        if (map.get("err_code") != null && map.get("err_code_des") != null) {
            log.error(map.get("err_code").toString());
            log.error(map.get("err_code_des").toString());
        }
        //server端再次签名
        wxOrderRequest.setNonce_str(map.get("nonce_str").toString());
        return responseDto.successData(PayUtil.wXResign(wxOrderContext, wxOrderRequest));
    }
    //@PostMapping("/wxPayOrder")
    public WxOrderResign wxPayOrder(Order orderPo,String openid) throws Exception {
        //统一下单	都需要先获取到Openid，调用相同的API
        log.error(wxOrderRequest.toString());
        WxOrderRequest realRequest = new WxOrderRequest();
        realRequest = BeanCopyUtil.copyProperties(wxOrderRequest, WxOrderRequest.class);
        realRequest.setOpenid(openid);
        realRequest.setOut_trade_no(orderPo.getOrderNumber());
        //orderPo.getPrice()暂时取前端传的price
        log.error("value" + orderPo.getPrice().multiply(new BigDecimal("100")).intValue());
        realRequest.setTotal_fee(orderPo.getPrice().multiply(new BigDecimal("100")).intValue());
        realRequest.setNonce_str(wxOrderRequest.getNonce_str());
        realRequest.setShopKey(wxOrderRequest.getShopKey());
        realRequest.setA_package(null);
        realRequest.setBody("记忆之声-购票");
        //已初始化
        realRequest.setNotify_url("http://47.92.115.105/order/receiveNotify");
        log.error("realRequest1" + realRequest.toString());
        Map<String, Object> map = PayUtil.sendPayment(realRequest);
        realRequest.setSign(map.get("sign").toString());
        //log.error("realRequest2" + realRequest.toString());
        realRequest.setTimeStamp(null);
        WxOrderContext wxOrderContext = WxOrderContext.builder()
                .return_code(map.get("return_code").toString())
                .return_msg(map.get("return_msg").toString())
                .result_code(map.get("result_code").toString())
                .trade_type(map.get("trade_type").toString())
                .prepay_id(map.get("prepay_id").toString())
                .build();
        if (map.get("err_code") != null && map.get("err_code_des") != null) {
            log.error(map.get("err_code").toString());
            log.error(map.get("err_code_des").toString());
        }
        //server端再次签名
        wxOrderRequest.setNonce_str(map.get("nonce_str").toString());
        return PayUtil.wXResign(wxOrderContext, wxOrderRequest);
    }

    public String getSign(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + wxOrderRequest.getShopKey();
        //Util.log("Sign Before MD5:" + result);
        result = encodeByMD5(result).toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }
    //监听接收支付结果
    @PostMapping("/receiveNotify")
    private String receiveNotify(HttpServletRequest httpServletRequest) {
        WxOrderContext wxOrderContext = new WxOrderContext();
        try {
            Map<String,String> messageMap = MessageUtil.parseXML(httpServletRequest);
            log.error("enter receiveNotify"+httpServletRequest.getInputStream().toString());
            for (Map.Entry<String, String> entry : messageMap.entrySet()) {
                log.error("messageMap:"+entry.getKey()+entry.getValue());
            }
            wxOrderContext.setReturn_code(messageMap.get("return_code"));
            wxOrderContext.setReturn_msg(messageMap.get("return_msg"));
            if (wxOrderContext.getReturn_code().equals("SUCCESS")) {
                wxOrderContext.setAppid(messageMap.get("appid"));
                wxOrderContext.setMch_id(messageMap.get("mch_id"));
                wxOrderContext.setDevice_info(messageMap.get("device_info"));
                wxOrderContext.setNonce_str(messageMap.get("nonce_str"));
                wxOrderContext.setSign(messageMap.get("sign"));
                wxOrderContext.setResult_code(messageMap.get("result_code"));
                wxOrderContext.setOpenid(messageMap.get("openid"));
                wxOrderContext.setTransaction_id(messageMap.get("transaction_id"));
                wxOrderContext.setTotal_fee(messageMap.get("total_fee"));
                wxOrderContext.setCash_fee(messageMap.get("cash_fee"));
                wxOrderContext.setOut_trade_no(messageMap.get("out_trade_no"));
                wxOrderContext.setTime_end(messageMap.get("time_end"));
            } else {
                return WxConstant.WX_PAY_FAILED_MESSAGE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return WxConstant.WX_PAY_FAILED_MESSAGE;
        }
        try {
            log.error("当前订单号"+wxOrderContext.getOut_trade_no());
            wxPaySucess(wxOrderContext.getOut_trade_no());
        } catch (BizException e) {
            return WxConstant.WX_PAY_SUCESSED_MESSAGE;
        }
        return WxConstant.WX_PAY_SUCESSED_MESSAGE;
    }
}
