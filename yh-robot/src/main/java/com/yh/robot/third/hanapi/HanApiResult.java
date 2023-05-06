package com.yh.robot.third.hanapi;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @ClassName HanApiResult
 * @Description TODO
 * @Author Y
 * @Date 2023/3/18 23:12
 * @Version 1.0
 */
@Data
public class HanApiResult {

    private Boolean success;

    private String imgurl;

    private String url;

    private JSONObject data;

}
