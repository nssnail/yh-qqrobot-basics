package com.yh.robot.handler.business.impl.freeapi;

import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.freeapi.FreeApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月17日 19:09:00
 */
@HandleService(value = "FreeApiShortHandler"
        ,handleType = HandleType.NOTHING
        ,sendType = SendMsgType.TEXT
        , handleName = "FreeApi短句")
public class FreeApiShortBusinessHandler implements RobotBusinessHandler {

    @Resource
    private FreeApi freeApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(freeApi.getYiyan());
//        RobotSendUtil.sendGroupMessage(data, groupId);
    }
}