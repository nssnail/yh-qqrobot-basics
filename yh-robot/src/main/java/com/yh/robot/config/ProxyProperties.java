package com.yh.robot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月19日 17:22:00
 */
@Component
@ConfigurationProperties(prefix = "proxy")
@Data
public class ProxyProperties {

    private Boolean enable;

    private String url;

    private Integer port;
}