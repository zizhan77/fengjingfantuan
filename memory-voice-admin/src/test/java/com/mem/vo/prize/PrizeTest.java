package com.mem.vo.prize;
import java.util.Date;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.mem.vo.ApplicationTests;
import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.PrizeD;
import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.model.vo.EticketVO;
import com.mem.vo.business.base.model.vo.IntegralVO;
import com.mem.vo.business.base.service.PrizeDService;
import com.mem.vo.business.base.service.RewardService;
import com.mem.vo.business.base.service.PrizeService;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-23 11:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
public class PrizeTest {

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private RewardService rewardService;

    @Test
    public void testInsert() {
        Prize prize = new Prize();
        prize.setPrizeName("1-100积分");
        prize.setProb(new BigDecimal("1"));
        prize.setTotalNum(10000);
        prize.setGivedNum(0);
        prize.setPrizeType(0);
        prize.setPrizeIntro("获得1-100积分");
        prize.setPrizeRule("http://www.baidu.com");
        System.out.println(JSON.toJSONString(prize));
        prizeService.insert(prize);
    }

    @Test
    public void testSelect() {
        RewardQuery query = new RewardQuery();
        query.setActivityId(Math.toIntExact(1));
        query.setPrizeType(PrizeEnum.TICKET.getCode());
        List<Integer> idList = rewardService.selectIdByEt(query);
        System.out.println(JsonUtil.toJson(idList));
    }

    @Resource
    private PrizeDService prizeDService;

    @Test
    public void testBatchInsert() {
        List<PrizeD> prizeList = new ArrayList<>();
        PrizeD prizeD = new PrizeD();
     //   prizeD.setId(0);
//        prizeD.setPrizeId(0);
        prizeD.setPrizeType(3);
        prizeD.setIntegralQty(100);
        prizeD.setProb(new BigDecimal("0.01"));
        prizeD.setEticketCode("");
        prizeD.setName("100积分奖励");
        prizeD.setActivityId(1L);
        prizeD.setPrizeIntro("这是一个积分奖励");
        prizeD.setPrizeName("积分奖励");

        List<IntegralVO> integralVOList = new ArrayList<>();
        IntegralVO integralVO = new IntegralVO();
        integralVO.setIntegralNum(100);
        integralVO.setProb(new BigDecimal("0.8"));
        integralVOList.add(integralVO);
        IntegralVO integralVO1 = new IntegralVO();
        integralVO1.setIntegralNum(100);
        integralVO1.setProb(new BigDecimal("0.2"));
        integralVOList.add(integralVO1);

        List<EticketVO> eticketVOS = new ArrayList<>();
        EticketVO eticketVO = new EticketVO();
        eticketVO.setNums(3);
        eticketVO.setName("蔡徐坤-支持选座-电子票/VIP/3份");
        EticketVO eticketVO1 = new EticketVO();
        eticketVO1.setNums(4);
        eticketVO.setName("蔡徐坤-支持选座-电子票/VIP/3份");
        eticketVOS.add(eticketVO);
        eticketVOS.add(eticketVO1);
        prizeD.setEticketList(eticketVOS);
        prizeD.setIntegralProbList(integralVOList);
        System.out.println(JsonUtil.toJson(prizeD));
       // System.out.println(prizeDService.insertBatch(prizeList));
    }
}
