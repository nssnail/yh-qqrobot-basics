package com.yh.robot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wyh
 * @Description TODO
 * @createTime 2023年05月04日 15:27:00
 */
@Component
@ConfigurationProperties(prefix = "verify")
@Data
public class VerifyProperties {

    private List<String> words;
}