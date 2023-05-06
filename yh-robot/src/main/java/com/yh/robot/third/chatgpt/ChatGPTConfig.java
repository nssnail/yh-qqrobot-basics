package com.yh.robot.third.chatgpt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月19日 14:27:00
 */
@Component
@ConfigurationProperties(prefix = "openai")
@Data
public class ChatGPTConfig {

    private List<String> apiKeys;

    private String proxyUrl;

    private String realUrl;

    private String use;

    private Integer expireTime;

}