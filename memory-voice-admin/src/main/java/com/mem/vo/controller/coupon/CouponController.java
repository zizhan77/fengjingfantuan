package com.mem.vo.controller.coupon;

import com.alibaba.fastjson.JSONObject;
import com.mem.vo.business.base.model.po.*;
import com.mem.vo.business.base.service.CouponRecordService;
import com.mem.vo.business.base.service.CouponService;
import com.mem.vo.business.base.service.ExchangeCodeMainService;
import com.mem.vo.business.biz.model.dto.CommonLoginContext;
import com.mem.vo.business.biz.model.vo.OrderVo;
import com.mem.vo.business.biz.model.vo.exchangecode.ExchangeCodeRequest;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.ExchangeCodeBizType;
import com.mem.vo.common.dto.Page;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.BeanCopyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
*
* <br>
* <b>功能：</b>CouponController<br>
* <br>
*/
@RestController
@RequestMapping("/coupon")
public class CouponController{

    private final static Logger log= LogManager.getLogger(CouponController.class);
    @Resource
    private CouponService couponService;
    @Resource
    private CouponRecordService couponRecordService;
    @Resource
    private ExchangeCodeMainService exchangeCodeMainService;
    @Resource
    private TokenService tokenService;

    @PostMapping("/getCouponListByPage")
    public ResponseDto<List<Coupon>> getCouponListByPage(@RequestHeader("token") String token, CouponQuery couponQuery) {

        //分页查询代码冗余，可优化
        ResponseDto<JSONObject> responseDto = ResponseDto.successDto();
        try {
            JSONObject jsonObject = new JSONObject();
            Page page = new Page();
            page.setPage(couponQuery.getPage());
            page.setLimit(couponQuery.getLimit());

            Page<Coupon> resPage = couponService.findPageByCondition(page, couponQuery);
            jsonObject.put("total", resPage.getTotal());
            jsonObject.put("list", resPage.getData());
            return responseDto.successData(jsonObject);
        } catch (BizException e) {
            log.error("分页获取优惠券列表异常，参数:{},原因：{}", couponQuery.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("分页优惠券列表异常，参数:{}", couponQuery.getId(), e);
            return responseDto.failSys();
        }
    }

    @PostMapping("/updateCouponModel")
    public ResponseDto<Integer> updateCouponModel(@RequestHeader("token") String token, Coupon coupon) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(coupon.getId(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(couponService.updateById(coupon));

        } catch (BizException e) {

            log.error("更新优惠券列表异常，参数:{},原因：{}", coupon.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("获取优惠券列表异常，参数:{}", coupon.getId(), e);
            return responseDto.failSys();
        }
    }
    @PostMapping("/addCouponModel")
    public ResponseDto<Integer> addCouponModel(@RequestHeader("token") String token, Coupon coupon) {

        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        try {
            BizAssert.notNull(coupon.getTitle(), BizCode.PARAM_NULL.getMessage());
            return responseDto.successData(couponService.insert(coupon));

        } catch (BizException e) {

            log.error("添加优惠券模板异常，参数:{},原因：{}", coupon.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("添加优惠券模板异常，参数:{}", coupon.getId(), e);
            return responseDto.failSys();
        }
    }
    @PostMapping("/createCouponExchangeCodeInBatch")
    public ResponseDto<String> createCouponExchangeCodeInBatch(
            @RequestHeader("token") String token, Coupon coupon,Integer num) {

        //权限验证
        ResponseDto<String> responseDto = ResponseDto.successDto();
        CommonLoginContext commonLoginContext= tokenService.getContextByken(token);
        try {
            BizAssert.notNull(coupon.getId(), BizCode.PARAM_NULL.getMessage());
            CouponRecord couponRecord = BeanCopyUtil.copyProperties(coupon, CouponRecord.class);
            couponRecord.setCouponId(coupon.getId()+"");
            List<String> recordIdList = new ArrayList<>();
            List<CouponRecord> couponRecordList = new ArrayList<>();
            for(int i=0;i<num;i++){
                recordIdList.add(couponRecordService.insert(couponRecord)+"");
                couponRecordList.add(couponRecord);
            }
            ExchangeCodeRequest exchangeCodeRequest = ExchangeCodeRequest.builder()
                    .businessKey(coupon.getId()+"")
                    .recordBusinessKeyList(recordIdList)
                    .businessTag(ExchangeCodeBizType.FOR_COUPON.getCode())
                    .number(1).build();

            //此时优惠券为假的userid，发放使用优惠券，需要再次调用下面方法，修改用户信息
            // ExchangeCodeMainService#updateRealUserByTagAndKey
            List<String> exCodeList = exchangeCodeMainService.generateCommonExchangeCode(commonLoginContext.getUserId(),exchangeCodeRequest);
            for (int i=0;i<num;i++){
                CouponRecord cur = couponRecordList.get(i);
                cur.setExchangeCode(exCodeList.get(i));
                cur.setId(Long.parseLong(recordIdList.get(i)));
                couponRecordList.add(cur);
                couponRecordList.remove(0);
            }
            //生成优惠券记录
            return responseDto.successData("批量生成优惠券成功:"+num);

        } catch (BizException e) {

            log.error("生成优惠券兑换码异常，参数:{},原因：{}", coupon.getId(), e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("生成优惠券兑换码异常，参数:{}", coupon.getId(), e);
            return responseDto.failSys();
        }
    }
}
