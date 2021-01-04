package com.mem.vo.business.base.model.vo;

import com.mem.vo.business.base.model.po.Express;
import lombok.Data;

@Data
public class UserDeliverInfoVOBuilder {
    private String name;

    private String phoneNumber;

    private String address;

    private String waybillCode;

    private Express express;
}
