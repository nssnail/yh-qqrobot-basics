package com.yh.robot.message.send;

import com.alibaba.fastjson.JSONObject;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月22日 09:34:00
 */
public abstract class SendMessage {

    public abstract String getAction();

    public abstract Object getParams();

    public String buildPostData(){
        return JSONObject.toJSONString(getParams());
    }

}