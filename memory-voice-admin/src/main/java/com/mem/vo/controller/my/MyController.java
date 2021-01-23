package com.mem.vo.controller.my;

import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.model.vo.OderDetailVO;
import com.mem.vo.business.base.model.vo.OderVO;
import com.mem.vo.business.base.model.vo.UserDeliverInfoVO;
import com.mem.vo.business.base.model.vo.UserVO;
import com.mem.vo.business.base.service.*;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.vo.exchangecode.CheckTicketVo;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.ActivityEnum;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.constant.TicketDeliverType;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.JsonUtil;
import com.mem.vo.common.util.QRCodeUtil;
import com.mem.vo.config.annotations.CommonExHandler;
import com.mem.vo.config.annotations.DontCheckLoginStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author lvxiao
 * @date 2019/6/29
 */
@RestController
@RequestMapping("/my")
@Slf4j
public class MyController {

    @Resource
    private UserService userService;
    @Resource
    private OrderService orderService;
    @Resource
    private TokenService tokenService;
    @Resource
    private IntegralService integralService;
    @Resource
    private PerformanceService performanceService;
    @Resource
    private TicketGearService ticketGearService;
    @Resource
    private BasicPlaceService basicPlaceService;
    @Resource
    private RewardService rewardService;
    @Resource
    private ExpressService expressService;
    @Resource
    private PerformanceShowService performanceShowService;

    @DontCheckLoginStatus
    @PostMapping("/findUserByToken")
    public ResponseDto<UserVO> findBUserByToken(@RequestHeader String token) {
        ResponseDto<UserVO> responseDto = ResponseDto.successDto();

        try {
            CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
            long userId = commonLoginContext.getUserId();
            BizAssert.notNull(commonLoginContext, "查询token信息为空");
            User user = userService.findById(userId);
            if (user == null) {
                throw new BizException(BizCode.BIZ_1009.getMessage());
            }
            UserVO userVO = UserVO.builder()
                    .id(user.getId())
                    .birthday(user.getBirthday())
                    .bizCode(user.getBizCode())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .gender(user.getGender())
                    .integral(user.getIntegral())
                    .build();
            return responseDto.successData(userVO);
        } catch (BizException e) {
            log.error("/user/queryByToken异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("/user/queryByToken异常", e);
            return responseDto.failSys();
        }
    }

    @Transactional
    @CommonExHandler(key = "修改用户信息")
    @PostMapping("/updateUserByToken")
    public ResponseDto<Integer> updateUserByToken(@RequestHeader String token, User user) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        long userId = commonLoginContext.getUserId();
        user.setId(userId);
        User user1 = userService.findById(userId);
        int row = userService.updateById(user);
        //只有完善才增加积分
        if (user1 != null && user1.getOverdata().toString().equals("0")) {
            if (row > 0 && user1 != null && user1.getBirthday() != null && user1.getGender() != null && user.getIsaddress() != null && user.getIsaddress().toString().equals("1")) {
//            if (row > 0 && user1 != null && StringUtils.isBlank(user1.getPhoneNumber()) && user1.getBirthday() == null && user1.getGender().equals(0)){
                //增加100积分
                Reward reward = Reward.builder()
                        .prizeType(PrizeEnum.INTEGRAL.getCode())
                        .userId(Math.toIntExact(userId))
                        .integralNum(PrizeEnum.DEFAULT_PRIZE.getCode())
                        .activityId(ActivityEnum.ACTIVITY_USER_INFO.getCode())
                        .prizeName(ActivityEnum.ACTIVITY_USER_INFO.getName() + "获得100积分")
                        .build();
                rewardService.insert(reward);
            }
        }
        return responseDto.successData(userService.updateById(user));
    }

    @CommonExHandler(key = "查询我的订单")
    @PostMapping("/findOrderListByToken")
    public ResponseDto<List<OderVO>> findOrderByToken(@RequestHeader String token, OrderQuery query) {
        ResponseDto<List<OderVO>> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
        BizAssert.notNull(query, "输入参数为空");
        query.setUserId(commonLoginContext.getUserId());
        return responseDto.successData(orderService.findMyOrderListByUserId(query));
    }

    @PostMapping("/findIntegralListByToken")
    public ResponseDto<List<Integral>> findIntegralListByToken(@RequestHeader String token) {
        ResponseDto<List<Integral>> responseDto = ResponseDto.successDto();
        try {
            CommonLoginContext commonLoginContext = tokenService.getContextByken(token);
            long userId = commonLoginContext.getUserId();
            BizAssert.notNull(commonLoginContext, "查询token信息为空");
            BizAssert.notNull(commonLoginContext.getUserId(), "token 保存  userid 为空");
            IntegralQuery query = new IntegralQuery();
            query.setUserId((int) userId);
            return responseDto.successData(integralService.findByCondition(query));
        } catch (BizException e) {
            log.error("/user/findIntegralListByToken异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("/user/findIntegralListByToken", e);
            return responseDto.failSys();
        }
    }

    @CommonExHandler(key = "查询订单详情")
    @PostMapping("/findOrderDetailById")
    public ResponseDto<OderDetailVO> findOrderDetailById(@RequestHeader String token, Long orderId) {
        ResponseDto<OderDetailVO> responseDto = ResponseDto.successDto();
        BizAssert.notNull(orderId, "参数为空");
        //虽然我感觉这样不是太好，但是我还是先在controller里写逻辑吧~~以后再优化O(∩_∩)O哈哈哈~
        Order order = orderService.findById(orderId);
        if (order == null) {
            throw new BizException("未能查到该订单");
        }
        Performance performance = performanceService.findById(order.getPerformanceId());
        TicketGear ticketGear = ticketGearService.findById(order.getTicketGearId());
        User user = userService.findById(order.getUserId());
        BasicPlace place = null;
        UserDeliverInfoVO userDeliverInfoVO = null;
        String qrCode = null;

        if (performance != null && performance.getPlaceId() != null) {
            place = basicPlaceService.findById(performance.getPlaceId());
        }
        Long showId = order.getShowId();
        PerformanceShow performanceShow;
        if (performance != null && showId != null && (performanceShow = performanceShowService.findById(showId)) != null) {
            Date firstShowTime = performanceShow.getStartTime();
            BizAssert.notNull(firstShowTime, "获取第一场演出时间为空");
            performance.setShowTime(firstShowTime);
            //状态转换
            Integer status = 0;
            status = performanceService.getStatus(performance.getStartSaleDate(), firstShowTime, status);
            performance.setStatus(status);
        }
        if (performance == null || performance.getTicketDeliverType() == null) {
            log.error("查询订单详情不能查询到演出信息，订单号{}", orderId);
        } else if (performance.getTicketDeliverType().equals(TicketDeliverType.Express.getCode())) {
            userDeliverInfoVO = UserDeliverInfoVO
                    .builder()
                    .address(order.getDeliverAddress())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .waybillCode(order.getWaybillCode())
                    .express(expressService.getExpressInfo(order.getWaybillCode()))
                    .build();
        } else {
            CheckTicketVo checkTicketVo = CheckTicketVo
                    .builder()
                    .exchangeCode(order.getWaybillCode())
                    .performanceId(performance.getId())
                    .userId(user.getId())
                    .build();
            try {
                qrCode = QRCodeUtil.encodeBase64(JsonUtil.toJson(checkTicketVo), null, true);
            } catch (Exception e) {
                log.error("生成二维码异常！", e);
                throw new BizException("生成二维码异常！异常信息:" + e.getMessage());
            }

        }

        OderDetailVO oderDetailVO = OderDetailVO
                .builder()
                .deliverInfo(userDeliverInfoVO)
                .order(order)
                .performance(performance)
                .ticketGear(ticketGear)
                .qrCode(qrCode)
                .basicPlace(place)
                .build();
        return responseDto.successData(oderDetailVO);
    }

}
