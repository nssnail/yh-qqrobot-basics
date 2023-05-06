package com.yh.web.controller.robot;

import com.alibaba.fastjson.JSONObject;
import com.yh.robot.message.result.RobotResult;
import com.yh.robot.service.MessageSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class RobotController {

    @Resource
    private MessageSendService messageSendService;

    @RequestMapping("/getMessage")
    public RobotResult getMessage(@RequestBody JSONObject jsonObject) {
        return messageSendService.sendMessage(jsonObject);
    }


}