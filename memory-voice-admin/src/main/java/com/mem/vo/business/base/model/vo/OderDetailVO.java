package com.mem.vo.business.base.model.vo;

import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.Order;
import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.TicketGear;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-07-04 23:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OderDetailVO {

    /**
     * 订单信息
     */
    private Order order;

    /**
     * 演出信息
     */
    private Performance performance;
    /**
     * 演出场地信息
     */
    private BasicPlace basicPlace;
    /**
     * 票档信息
     */
    private TicketGear ticketGear;

    /**
     * 配送信息
     */
    private UserDeliverInfoVO deliverInfo;

    /**
     * 二维码
     */
    private String qrCode;
}
