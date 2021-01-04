package com.mem.vo.business.base.model.vo;

import com.mem.vo.business.base.model.po.BasicPlace;
import com.mem.vo.business.base.model.po.Order;
import com.mem.vo.business.base.model.po.Performance;
import com.mem.vo.business.base.model.po.TicketGear;
import lombok.Data;

@Data
public class OderDetailVOBuilder {
    private Order order;

    private Performance performance;

    private BasicPlace basicPlace;

    private TicketGear ticketGear;

    private UserDeliverInfoVO deliverInfo;

    private String qrCode;
}
