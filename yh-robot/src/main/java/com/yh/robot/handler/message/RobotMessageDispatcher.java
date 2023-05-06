package com.yh.robot.handler.message;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.message.result.RobotResult;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月20日 14:37:00
 */
public interface RobotMessageDispatcher {

    RobotResult dispatcher(JSONObject jsonObject);
}