package com.mem.vo.business.base.service;

import com.mem.vo.business.base.model.po.Express;

import java.util.Map;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-08-09 21:22
 */
public interface ExpressService {
    Express getExpressInfo(String waybillCode);
}
