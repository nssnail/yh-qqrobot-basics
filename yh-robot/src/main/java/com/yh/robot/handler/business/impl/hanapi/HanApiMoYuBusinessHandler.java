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
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月20日 11:15:00
 */
@HandleService(value = "HanApiMoYuHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.IMAGE
        , handleName = "HanApi摸鱼图片")
public class HanApiMoYuBusinessHandler implements RobotBusinessHandler {

    @Resource
    private HanApi hanApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(hanApi.getMOYUImage());
//        RobotSendUtil.sendGroupImageMessage(moyu,groupId);
    }
}