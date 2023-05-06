package com.yh.robot.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月21日 14:29:00
 */
public interface RobotSendService {

    void send(JSONObject jsonObject);

}