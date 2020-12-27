package com.mem.vo.express;

import com.mem.vo.BaseTest;
import com.mem.vo.business.base.model.po.Express;
import com.mem.vo.business.base.service.ExpressService;
import com.mem.vo.common.util.JsonUtil;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-08-09 21:44
 */
public class ExpressReturnTest extends BaseTest {
    @Resource
    private ExpressService expressService;

    @Test
    public void test(){
        Express express = expressService.getExpressInfo("73117458444462");
        System.out.println(JsonUtil.toJson(express));
    }
}
