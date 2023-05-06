package com.yh.robot.config;

import com.yh.robot.filter.MessageFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.Collections;

/**
 * @author nssnail
 * @Description web配置
 * @createTime 2023年03月16日 14:18:00
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> baseFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MessageFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}