package com.yh.file.config;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 14:50:00
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file.tx")
@Data
public class TxConfig {

    private String secretId ;

    private String secretKey ;

    private String bucketName ;

    private String regionName ;

    private String projectName;
}