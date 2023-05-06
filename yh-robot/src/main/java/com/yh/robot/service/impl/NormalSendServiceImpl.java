package com.yh.robot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.message.result.RobotResult;
import com.yh.robot.service.MessageSendService;
import com.yh.robot.service.RobotSendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月21日 14:30:00
 */
@Service("NormalSendService")
public class NormalSendServiceImpl implements RobotSendService {

    @Resource
    private MessageSendService messageSendService;

    @Override
    public void send(JSONObject jsonObject) {
        messageSendService.handle(jsonObject);
    }
}