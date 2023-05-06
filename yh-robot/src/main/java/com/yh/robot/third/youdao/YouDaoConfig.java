package com.yh.robot.third.youdao;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName YouDaoConfig
 * @Description TODO
 * @Author Y
 * @Date 2023/3/18 10:57
 * @Version 1.0
 */
@Configuration
@Data
public class YouDaoConfig {

    @Value("${youdaoApi.url}")
    private String url;
    @Value("${youdaoApi.appKey}")
    private String appKey;
    @Value("${youdaoApi.appSecret}")
    private String appSecret;
}
