package com.yh.robot.handler.business.impl.xiaobaiapi;

import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.xiaobaiapi.XiaoBaiApi;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月10日 14:23:00
 */
@HandleService(value = "XiaoBaiApiJuTouBusinessHandler"
        , handleType = HandleType.REMOVE
        , sendType = SendMsgType.IMAGE
        , handleName = "xiaobaiAPi 举头")
public class XiaoBaiApiJuTouBusinessHandler implements RobotBusinessHandler {

    @Resource
    private XiaoBaiApi xiaoBaiApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(xiaoBaiApi.getJuTou(messageVo.getMsg()));
    }
}