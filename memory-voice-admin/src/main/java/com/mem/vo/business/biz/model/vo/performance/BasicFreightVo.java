package com.mem.vo.business.biz.model.vo.performance;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/3 15:36
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicFreightVo {

    /**
     *
     */
    private Long id;
    /**
     * 目的省编码
     */
    private String oneAddress;

    /**
     * 目的省编码
     */
    private String oneAddressName;
    /**
     * 运费金额
     */
    private BigDecimal freight;

    /**
     * 创建时间
     */
    private Date createTime;

}
