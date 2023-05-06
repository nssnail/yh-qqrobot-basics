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
 * @createTime 2023年04月10日 16:29:00
 */
@HandleService(value = "XiaoBaiApiMMVideoBusinessHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.VIDEO
        , handleName = "xiaobaiAPI 美女视频")
public class XiaoBaiApiMMVideoBusinessHandler implements RobotBusinessHandler {

    @Resource
    private XiaoBaiApi xiaoBaiApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        return Collections.singletonList(xiaoBaiApi.getMMVideo());
    }
}