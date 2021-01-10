package com.mem.vo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-30 17:08
 */
@Component
@ConfigurationProperties(prefix = "aliyuncs")
@Data
public class AliyuncsConfig {
    private String accessKeyId;
    private String accessSecret;
    private String domain;
    private String version;
    private String action;
    private String templateCode;
    private String signName;
    private String regionId;
}
