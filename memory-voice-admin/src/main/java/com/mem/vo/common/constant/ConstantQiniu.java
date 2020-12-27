package com.mem.vo.common.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-17 23:26
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class ConstantQiniu {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String domainOfBucket;

    private String zone;
}
