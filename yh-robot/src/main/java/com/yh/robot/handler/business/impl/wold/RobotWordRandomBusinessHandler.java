package com.yh.robot.handler.business.impl.wold;

import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.service.RobotWordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@HandleService(value = "RobotWordRandomHandler"
        , handleType = HandleType.SUB
        , sendType = SendMsgType.TEXT
        , handleName = "随机英语单词处理器"
        , isApi = false)
public class RobotWordRandomBusinessHandler implements RobotBusinessHandler {

    @Resource
    private RobotWordService robotWordService;

    @Override
    public List<String> handle(MessageVo messageVo) {
        String msg = messageVo.getMsg();
        if (StringUtils.isBlank(msg)) {
            msg = "10";
        }
        return Collections.singletonList(robotWordService.randomWord(Integer.parseInt(msg)));
//        RobotSendUtil.sendGroupMessage(data, groupId);
    }
}