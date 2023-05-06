package com.yh.robot.handler.business.impl.xiaobaiapi;

import cn.hutool.core.lang.Assert;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.third.xiaobaiapi.XiaoBaiApi;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月11日 14:25:00
 */
@HandleService(value = "XiaoBaiApiQianTouBusinessHandler"
        , handleType = HandleType.REMOVE
        , sendType = SendMsgType.IMAGE
        , handleName = "xiaobaiAPi 牵头")
public class XiaoBaiApiQianTouBusinessHandler implements RobotBusinessHandler {

    @Resource
    private XiaoBaiApi xiaoBaiApi;

    @Override
    public List<String> handle(MessageVo messageVo) {
        String[] split = messageVo.getMsg().split(",");
        Assert.isTrue(split.length == 2, () -> BotException.error("参数错误",messageVo.getUserId(),messageVo.getGroupId()));
        return Collections.singletonList(xiaoBaiApi.getQianTou(split[0], split[1]));
    }
}