package com.mem.vo.common.util;

import com.mem.vo.business.base.model.po.Prize;
import com.mem.vo.business.base.model.po.PrizeD;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽奖工具类
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-01 14:52
 */
public class DrawLotteryUtil {

    public static int drawPrize(List<Prize> prizeList) {
        if (CollectionUtils.isEmpty(prizeList)) {
            return -1;
        }
        List<Double> orgProbList = new ArrayList<Double>(prizeList.size());
        for (Prize Prize : prizeList) {
            //按顺序将概率添加到集合中
            orgProbList.add(Prize.getProb().doubleValue());
        }
        return draw(orgProbList);
    }

    public static int drawPrizeD(List<PrizeD> prizeDList) {
        if (CollectionUtils.isEmpty(prizeDList)) {
            return -1;
        }
        List<Double> orgProbList = new ArrayList<Double>(prizeDList.size());
        for (PrizeD Prize : prizeDList) {
            //按顺序将概率添加到集合中
            orgProbList.add(Prize.getProb().doubleValue());
        }
        return draw(orgProbList);
    }

    private static int draw(List<Double> PrizeProbList) {
        List<Double> sortRateList = new ArrayList<Double>();
        // 计算概率总和
        Double sumRate = 0D;
        for (Double prob : PrizeProbList) {
            sumRate += prob;
        }

        if (sumRate != 0) {
            double rate = 0D;   //概率所占比例
            for (Double prob : PrizeProbList) {
                rate += prob;
                // 构建一个比例区段组成的集合(避免概率和不为1)
                sortRateList.add(rate / sumRate);
            }
            // 随机生成一个随机数，并排序
            double random = Math.random();
            sortRateList.add(random);
            Collections.sort(sortRateList);

            // 返回该随机数在比例集合中的索引
            return sortRateList.indexOf(random);
        }
        return -1;
    }

    public static void main(String[] args) {
        Prize iphone = new Prize();
        iphone.setId(101);
        iphone.setPrizeName("苹果手机");
        iphone.setProb(new BigDecimal(0.01D));

        Prize thanks = new Prize();
        thanks.setId(102);
        thanks.setPrizeName("再接再厉");
        thanks.setProb(new BigDecimal(0.5D));

        Prize vip = new Prize();
        vip.setId(103);
        vip.setPrizeName("优酷会员");
        vip.setProb(new BigDecimal(0.4D));

        List<Prize> list = new ArrayList<>();
        list.add(vip);
        list.add(thanks);
        list.add(iphone);

        for (int i = 0; i < 100; i++) {
            int index = drawPrize(list);
            System.out.println(list.get(index));
        }
    }

}

