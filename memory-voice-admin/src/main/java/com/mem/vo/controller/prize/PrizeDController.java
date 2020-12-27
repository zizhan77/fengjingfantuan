package com.mem.vo.controller.prize;

import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.PrizeDQuery;
import com.mem.vo.business.base.model.po.PrizeQuery;
import com.mem.vo.business.base.model.vo.EticketVO;
import com.mem.vo.business.base.model.vo.IntegralVO;
import com.mem.vo.business.base.service.PrizeDService;
import com.mem.vo.business.base.service.PrizeService;
import com.mem.vo.common.constant.BizCode;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizAssert;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-03 00:45
 */
@RestController
@RequestMapping("/prizeD")
@Slf4j
public class PrizeDController {

    @Resource
    private PrizeDService prizeDService;
    @Resource
    private PrizeService prizeService;

    /**
     * @Transactional
     * @CommonExHandler(key = "插入奖品")
     * @PostMapping("/insertBatch") public ResponseDto<Integer> inset(@RequestHeader("token") String token, @RequestBody List<PrizeD> prizes) {
     * //权限验证
     * ResponseDto<Integer> responseDto = ResponseDto.successDto();
     * if (CollectionUtils.isEmpty(prizes)) {
     * throw new BizException("输入参数为空");
     * }
     * PrizeD prized0 = prizes.get(0);
     * BizAssert.notNull(prized0.getActivityId(), BizCode.PARAM_NULL.getMessage());
     * BizAssert.notNull(prized0.getPrizeType(), BizCode.PARAM_NULL.getMessage());
     * for (PrizeD prizeD : prizes) {
     * if (prizeDService.insert(prizeD) == 0) {
     * throw new BizException("批量插入奖品失败");
     * }
     * }
     * //判断奖品该类奖品是否存在
     * PrizeQuery prizeQuery = PrizeQuery.builder()
     * .activityId(prized0.getActivityId())
     * .prizeType(prized0.getPrizeType())
     * .build();
     * List<Prize> prizeList = prizeService.findByCondition(prizeQuery);
     * if (CollectionUtils.isEmpty(prizeList)) {
     * //插入奖品主表
     * Prize prize = Prize.builder()
     * .activityId(prized0.getActivityId())
     * .prizeType(prized0.getPrizeType())
     * .prizeIntro(prized0.getPrizeIntro())
     * .prizeName(prized0.getPrizeName())
     * .totalNum(prizes.size())
     * .prob(prized0.getProb())
     * .build();
     * return responseDto.successData(prizeService.insert(prize));
     * }
     * Prize prize = prizeList.get(0);
     * prize.setTotalNum(prize.getTotalNum() + prizes.size());
     * return responseDto.successData(prizeService.updateById(prize));
     * }
     **/
    @Transactional
    @CommonExHandler(key = "更新奖品")
    @PostMapping("/updateBatch")
    public ResponseDto<Integer> update(@RequestHeader("token") String token,@RequestBody PrizeD prizeD) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        Long activityId = prizeD.getActivityId();
        BizAssert.notNull(activityId, BizCode.PARAM_NULL.getMessage());
        Integer prizeType = prizeD.getPrizeType();
        BizAssert.notNull(prizeType, BizCode.PARAM_NULL.getMessage());
        //增量
        int increaseNum = 0;
        //判断奖品该类奖品是否存在
        PrizeQuery prizeQuery = PrizeQuery.builder()
                .activityId(activityId)
                .prizeType(prizeType)
                .build();
        List<Prize> prizeList = prizeService.findByCondition(prizeQuery);
        if (prizeType.equals(PrizeEnum.INTEGRAL.getCode())) {
            if (CollectionUtils.isEmpty(prizeD.getIntegralProbList())) {
                throw new BizException("传入积分信息为空");
            }
            for (IntegralVO integralVO : prizeD.getIntegralProbList()) {
                //更新积分信息
                prizeD.setIntegralQty(integralVO.getIntegralNum());
                prizeD.setProb(integralVO.getProb());
                //插入积分
                if (integralVO.getId() == null) {
                    prizeDService.insert(prizeD);
                } else {
                    //更新积分信息
                    PrizeD prizeD1 = new PrizeD();
                    BeanUtils.copyProperties(prizeD, prizeD1);
                    prizeD1.setId(integralVO.getId());
                    prizeDService.updateById(prizeD1);
                }
            }

        } else if (prizeType.equals(PrizeEnum.MATERIAL.getCode())) {
            //实物奖励因为prize表有库存，只要保证prizeD表库存不小于prize库存即可，实际prizeD无意义。
            if (prizeD.getId() == null) {
                for (int i = 0; i < prizeD.getPrizeNum(); i++) {
                    prizeDService.insert(prizeD);
                }
            }
            if (CollectionUtils.isNotEmpty(prizeList)) {
                Prize prize = prizeList.get(0);
                //传入大于现有库存
                if (prizeD.getPrizeNum().compareTo(prize.getTotalNum()) > 0) {
                    for (int i = 0; i < prizeD.getPrizeNum() - prize.getTotalNum(); i++) {
                        prizeDService.insert(prizeD);
                    }
                }
            }
            increaseNum += prizeD.getPrizeNum();
        } else if (prizeType.equals(PrizeEnum.TICKET.getCode())) {
            //电子票不能删除
            if (CollectionUtils.isEmpty(prizeD.getEticketList())) {
                throw new BizException("传入明细为空");
            }
            for (EticketVO eticketVO : prizeD.getEticketList()) {
                int etnum = eticketVO.getNums() == null ? 0 : eticketVO.getNums();
                increaseNum += etnum;
                prizeD.setName(eticketVO.getName());
                for (int i = 0; i < etnum; i++) {
                    prizeDService.insert(prizeD);
                }
            }
        }
        if (CollectionUtils.isEmpty(prizeList)) {
            //插入奖品主表
            Prize prize = Prize.builder()
                    .activityId(prizeD.getActivityId())
                    .prizeType(prizeD.getPrizeType())
                    .prizeIntro(prizeD.getPrizeIntro())
                    .prizeName(prizeD.getPrizeName())
                    .totalNum(increaseNum)
                    .prob(prizeD.getProb())
                    .build();
            return responseDto.successData(prizeService.insert(prize));
        } else {
            Prize prize = prizeList.get(0);
            //设置总数量
            prize.setPrizeName(prizeD.getPrizeName());
            prize.setProb(prizeD.getProb());
            //还要判断是否增加了电子票
            if (prizeType.equals(PrizeEnum.TICKET.getCode())) {
                prize.setTotalNum(prize.getTotalNum() + increaseNum);
            } else {
                prize.setTotalNum(increaseNum);
            }
            return responseDto.successData(prizeService.updateById(prize));
        }
    }

    @CommonExHandler(key = "查询奖品")
    @PostMapping("/queryList")
    public ResponseDto<PrizeD> queryList(@RequestHeader("token") String token, PrizeDQuery query) {
        //权限验证
        ResponseDto<PrizeD> responseDto = ResponseDto.successDto();
        List<PrizeD> prizeDList = prizeDService.findByCondition(query);
        PrizeD res = new PrizeD();
        if (CollectionUtils.isEmpty(prizeDList) || query.getPrizeType().equals(PrizeEnum.MATERIAL.getCode())) {
            PrizeQuery prizeQuery = PrizeQuery
                    .builder()
                    .prizeType(query.getPrizeType())
                    .activityId(query.getActivityId())
                    .build();
            List<Prize> prizeList = prizeService.findByCondition(prizeQuery);
            if (CollectionUtils.isEmpty(prizeList)) {
                throw new BizException("没有找到该奖品的设置！");
            }
            Prize prize = prizeList.get(0);
            res.setProb(prize.getProb());
            res.setPrizeName(prize.getPrizeName());
            res.setPrizeType(prize.getPrizeType());
            res.setActivityId(prize.getActivityId());
            res.setPrizeNum(prize.getTotalNum());
            return responseDto.successData(res);
        }
        res = prizeDList.get(0);
        if (prizeDList.get(0).getPrizeType().equals(PrizeEnum.INTEGRAL.getCode())) {
            List<IntegralVO> integralProbList = new ArrayList<>();
            for (PrizeD d : prizeDList) {
                IntegralVO integralVO = new IntegralVO();
                integralVO.setId(d.getId());
                integralVO.setIntegralNum(d.getIntegralQty());
                integralVO.setProb(d.getProb());
                integralProbList.add(integralVO);
            }
            res.setIntegralProbList(integralProbList);
        } else if (prizeDList.get(0).getPrizeType().equals(PrizeEnum.TICKET.getCode())) {
            List<EticketVO> eticketVOList = new ArrayList<>();
            for (PrizeD prizeD : prizeDList) {
                EticketVO eticketVO = new EticketVO();
                eticketVO.setName(prizeD.getName());
                eticketVOList.add(eticketVO);
            }
            res.setEticketList(eticketVOList);
        }
        return responseDto.successData(res);
    }

    @CommonExHandler(key = "删除奖品")
    @PostMapping("/delete")
    public ResponseDto<Integer> delete(@RequestHeader("token") String token, List<Long> ids) {
        //权限验证
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        if (CollectionUtils.isEmpty(ids)) {
            throw new BizException("传入信息为空");
        }
        return responseDto.successData(prizeDService.deleteById(ids));
    }

}
