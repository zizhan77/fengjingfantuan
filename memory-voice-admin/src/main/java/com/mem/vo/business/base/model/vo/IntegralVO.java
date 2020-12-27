package com.mem.vo.business.base.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-06 14:56
 */
@Data
public class IntegralVO {
    /**
     * 明细id
     */
    private Integer id;
    /**
     * 积分数量
     */
    private Integer integralNum;
    /**
     * 概率
     */
    private BigDecimal prob;
}
