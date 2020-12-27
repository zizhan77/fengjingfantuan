package com.mem.vo.config;

import com.mem.vo.business.biz.model.dto.WxOrderRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author byh
 * @description:
 * @date 2019/5/31 17:55
 */

@Configuration
@ConditionalOnClass({WxOrderRequest.class})
public class WxOrderRequestConfigure {


    @Bean(name="wxOrderRequest")
    @ConfigurationProperties(prefix="wx.order")
    public WxOrderRequest wxOrderRequest() {
        return new WxOrderRequest();
    }
}
