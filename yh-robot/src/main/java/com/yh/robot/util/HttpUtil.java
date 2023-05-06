package com.yh.robot.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yh.robot.config.ProxyProperties;
import com.yh.robot.third.xiaobaiapi.XiaoBaiApiConstant;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;

public class HttpUtil {

    private static final RestTemplate restTemplate;

    static {
        ProxyProperties proxyProperties = SpringUtil.getBean(ProxyProperties.class);
        if(!proxyProperties.getEnable()){
            restTemplate = SpringUtil.getBean("defaultRestTemplate",RestTemplate.class);
        }else {
            restTemplate = SpringUtil.getBean("proxyRestTemplate",RestTemplate.class);
        }
    }

    /**
     * @Param url 请求路径
     * @Description get方法
     * @Date 11:48 2020/11/12
     **/
    public static String get(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.getForEntity(url, String.class, requestEntity).getBody();
    }

    public static <T> T get(String url, Class<T> clazz) {
        return JSONObject.parseObject(get(url), clazz);
    }

    /**
     * @Param url 请求路径，json 参数
     * @Description post方法
     * @Date 11:48 2020/11/12
     **/
    public static String post(String url, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class).getBody();
    }

    public static <T> T post(String url, String json, Class<T> clazz) {
        return JSONObject.parseObject(post(url, json), clazz);
    }


    public static String post(String url, String json, HttpHeaders headers) {
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class).getBody();
    }

    public static <T> T post(String url, String json, HttpHeaders headers, Class<T> clazz) {
        return JSONObject.parseObject(post(url, json,headers), clazz);
    }


    public static String post(String url, HashMap param, HttpHeaders headers) {
        String json = JSON.toJSONString(param);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class).getBody();
    }

    public static <T> T post(String url, HashMap param, HttpHeaders headers, Class<T> clazz) {
        return JSONObject.parseObject(post(url, param, headers), clazz);
    }

    /**
     * @Param url 请求路径，map 参数
     * @Description post方法
     * @Date 11:48 2020/11/12
     **/
    public static String post(String url, HashMap param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String json = JSON.toJSONString(param);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class).getBody();
    }

    public static <T> T post(String url, HashMap param, Class<T> clazz) {
        return JSONObject.parseObject(post(url, param), clazz);
    }


    /**
     * @Param url 请求路径，fields 表单属性，files 表单文件路径
     * @Description post方法
     * @Date 11:48 2020/11/12
     **/
    public static String post(String url, HashMap<String, Object> fields, HashMap<String, String> files) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        fields.forEach(params::add);

        files.forEach((k, v) -> {
            if (fields.containsKey(k)) {
                throw new RuntimeException("文件名与属性名含有相同字段");
            }
            params.add(k, createStreamContent(v));
        });
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
    }

    private static FileSystemResource createStreamContent(String path) {
        return new FileSystemResource(new File(path));
    }


    public static String buildParam(HashMap<String, Object> map) {
        if (CollectionUtil.isNotEmpty(map)) {
            StringBuilder sb = new StringBuilder("?");
            map.forEach((k, v) -> {
                sb.append(k).append("=").append(v).append("&");
            });
            return StrUtil.removeSuffix(sb.toString(), "&");
        }
        return null;
    }


    public static byte[] getByte(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                byte[].class);
        return response.getBody();
    }

}
