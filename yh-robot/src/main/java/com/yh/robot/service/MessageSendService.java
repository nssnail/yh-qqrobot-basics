package com.yh.robot.service;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.message.result.RobotResult;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 17:38:00
 */
public interface MessageSendService {

    RobotResult sendMessage(JSONObject jsonObject);

    void handle(JSONObject jsonObject);

}