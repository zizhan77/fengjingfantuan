package com.mem.vo.config;

import com.mem.vo.business.biz.model.dto.WxLoginRequest;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author litongwei
 * @description:
 * @date 2019/5/31 17:55
 */

@Configuration
@ConditionalOnClass({WxLoginRequest.class})
public class WxLoginRequestConfigure {


    @Bean(name="wxLoginRequest")
    @ConfigurationProperties(prefix="wx.login")
    public WxLoginRequest wxLoginRequest() {
        return new WxLoginRequest();
    }
}
