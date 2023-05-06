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
 * @ClassName FreeApiDouyiMMHandler
 * @Description TODO
 * @Author Y
 * @Date 2023/3/26 10:02
 * @Version 1.0
 */
@HandleService(value = "FreeApiDouyiMMHandler"
        ,handleType = HandleType.NOTHING
        ,sendType = SendMsgType.VIDEO
        , handleName = "抖音美女")
public class FreeApiDouyiMMHandler implements RobotBusinessHandler {

    @Resource
    private FreeApi freeApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(freeApi.getDouyinMM());
    }
}
