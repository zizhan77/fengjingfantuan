package com.mem.vo.business.base.model.vo;

import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.Order;
import lombok.Data;

/**
 * @author lvxiao
 * @date 2019/7/5
 */
@Data
public class OderVO extends Order {

    /**
     * 场地信息
     */
    private BasicPlace place;

    private PerformanceOrderVo performance;

}
