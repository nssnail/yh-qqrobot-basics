package com.yh.robot.third.hanapi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 11:59:00
 */
@Data
public class HanApiArrayResult {

    private Boolean success;

    private String imgurl;

    private String url;

    private String time;

    private JSONArray data;
}