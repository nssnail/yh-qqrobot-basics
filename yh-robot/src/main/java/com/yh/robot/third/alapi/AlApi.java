package com.yh.robot.third.alapi;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @ClassName AlApi
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 23:00
 * @Version 1.0
 */
@Component
public class AlApi {

    @Value("${alapi.token}")
    private String token;


    public AlApiResult send(String url){
        HashMap<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("format","json");
        String result = HttpUtil.post(url, map);
        return JSONObject.parseObject(result,AlApiResult.class);
    }
}
