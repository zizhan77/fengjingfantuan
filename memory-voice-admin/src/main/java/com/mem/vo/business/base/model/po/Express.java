package com.mem.vo.business.base.model.po;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-08-10 21:50
 */
@Data
public class Express {
    private String issign;
    private String number;
    private String expName;
    private String deliverystatus;
    private String courierPhone;
    private String type;
    private List<Map<String,Object>> list;
}
