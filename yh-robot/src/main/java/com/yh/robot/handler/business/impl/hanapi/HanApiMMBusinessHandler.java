package com.yh.robot.handler.business.impl.hanapi;

import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.hanapi.HanApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName HanApiMMHandler
 * @Description TODO
 * @Author Y
 * @Date 2023/3/18 23:24
 * @Version 1.0
 */
@HandleService(value = "HanApiMMHandler"
        ,handleType = HandleType.NOTHING
        ,sendType = SendMsgType.IMAGE
        , handleName = "HanAPI cosplay图片")
public class HanApiMMBusinessHandler implements RobotBusinessHandler {

    @Resource
    private HanApi hanApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(hanApi.getMMImage());
//        RobotSendUtil.sendGroupImageMessage(mmImage,groupId);
    }
}
