package com.yh.robot.config;

import com.yh.robot.third.chatgpt.ChatGPTConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月19日 16:54:00
 */
@Configuration
public class RestTemplateConfig {

    @Resource
    private ProxyProperties proxyProperties;

    @Bean("proxyRestTemplate")
    public RestTemplate getProxyTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyProperties.getUrl(), proxyProperties.getPort()));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60 * 3 * 1000); //设置连接超时时间
        requestFactory.setReadTimeout(60 * 3 * 1000); //设置读取超时时间
        requestFactory.setProxy(proxy);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean("defaultRestTemplate")
    public RestTemplate getDefaultTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60 * 3 * 1000); //设置连接超时时间
        requestFactory.setReadTimeout(60 * 3 * 1000); //设置读取超时时间
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}