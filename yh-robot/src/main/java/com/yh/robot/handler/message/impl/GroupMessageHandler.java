package com.yh.robot.handler.message.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.domain.RobotGroup;
import com.yh.robot.enums.StateEnum;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.handler.message.RobotMessageHandler;
import com.yh.robot.message.receive.GroupMessage;
import com.yh.robot.message.receive.Message;
import com.yh.robot.service.RobotGroupService;
import com.yh.robot.util.CQCodeUtil;
import com.yh.robot.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @ClassName GroupMessageHandler
 * @Description TODO
 * @Author Y
 * @Date 2023/3/21 23:11
 * @Version 1.0
 */
@Service
@Slf4j
public class GroupMessageHandler extends RobotMessageHandler {

    @Value("${qq}")
    private Long qq;
    @Resource
    private RobotGroupService robotGroupService;

    @Override
    public void handler(Message message) {
        Assert.isTrue(message instanceof GroupMessage, () -> BotException.error("系统异常，消息处理错误"));
        GroupMessage groupMessage = (GroupMessage) message;
        RobotGroup robotGroup = robotGroupService.getOne(Wrappers.<RobotGroup>lambdaQuery()
                .eq(RobotGroup::getGroupId, groupMessage.getGroupId()));
        if (CQCodeUtil.checkAt(groupMessage.getRawMessage(), qq)) {
            if (ObjectUtils.isEmpty(robotGroup) || robotGroup.getStatus() == StateEnum.DISABLE.getType()) {
                log.error("该群聊已被禁用=>{}", groupMessage.getGroupId());
                return;
            }
            String msg = CQCodeUtil.getAtMeMessage(groupMessage.getRawMessage(), qq);
            BusinessInvokeVo invoke = getData(new MessageVo(msg, groupMessage.getGroupId(), groupMessage.getUserId()));
            invoke.setMessageType(RobotMessageConstant.MessageType.GROUP);
            Method method = CommonUtil.getRobotClientMethod(invoke);
            CommonUtil.invokeRobotSend(method,robotClient,invoke,groupMessage.getGroupId());
        }
    }
}