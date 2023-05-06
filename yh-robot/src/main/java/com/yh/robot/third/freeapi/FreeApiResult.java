package com.yh.robot.third.freeapi;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @ClassName AlApiResult
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 23:06
 * @Version 1.0
 */
@Data
public class FreeApiResult {
    private Integer code;
    private  String msg;
    private JSONObject data;
    private String url;
}
