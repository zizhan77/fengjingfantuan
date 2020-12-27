package com.mem.vo.business.base.model.vo;

import com.mem.vo.business.base.model.po.Performance;
import lombok.Data;

import java.util.Date;

/**
 * @author lvxiao
 * @date 2019/7/24
 */
@Data
public class PerformanceOrderVo extends Performance {
    /**
     * 演出时间
     */
    private Date showTime;
}
