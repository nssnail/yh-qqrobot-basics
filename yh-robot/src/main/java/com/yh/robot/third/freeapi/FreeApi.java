package com.yh.robot.third.freeapi;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;


/**
 * @ClassName FreeApi
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 23:20
 * @Version 1.0
 */
@Component
public class FreeApi {

    @Value("${freeApi.appId}")
    private String appId;
    @Value("${freeApi.appSecret}")
    private String appSecret;

    public String getYiyan() {
        return HttpUtil.get(FreeConstant.YIYAN);
    }

    public FreeApiResult getTianQi(String city) {
        String result = HttpUtil.get(FreeConstant.getTianqi(city, getParam()));
        return JSONObject.parseObject(result, FreeApiResult.class);
    }


    public String getDouyinMM() {
        String result = HttpUtil.get(FreeConstant.DOUYIN_MM);
        FreeApiResult freeApiResult = JSONObject.parseObject(result, FreeApiResult.class);
        return freeApiResult.getUrl();
    }

    public String getParam() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("app_id", appId);
        map.put("app_secret", appSecret);
        return HttpUtil.buildParam(map);
    }
}
