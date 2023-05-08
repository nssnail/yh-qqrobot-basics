package com.yh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author yh
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YhApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(YhApplication.class, args);
        System.out.println("yh-qqrobot启动成功");
    }
}
