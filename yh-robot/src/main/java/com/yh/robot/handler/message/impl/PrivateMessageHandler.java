package com.yh.robot.handler.message.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.common.exception.robot.BotException;
import com.yh.robot.domain.RobotFriends;
import com.yh.robot.domain.RobotGroup;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.handler.business.vo.BusinessInvokeVo;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.handler.message.RobotMessageHandler;
import com.yh.robot.message.receive.Message;
import com.yh.robot.message.receive.PrivateMessage;
import com.yh.robot.service.RobotFriendsService;
import com.yh.robot.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @ClassName PrivateMessageHandler
 * @Description TODO
 * @Author Y
 * @Date 2023/3/21 23:12
 * @Version 1.0
 */
@Service
@Slf4j
public class PrivateMessageHandler extends RobotMessageHandler {

    @Resource
    private RobotFriendsService robotFriendsService;

    @Override
    public void handler(Message message) {
        Assert.isTrue(message instanceof PrivateMessage, () -> BotException.error("系统异常，消息处理错误"));
        PrivateMessage privateMessage = (PrivateMessage) message;
        RobotFriends robotFriend = robotFriendsService.getOne(Wrappers.<RobotFriends>lambdaQuery()
                .eq(RobotFriends::getQq, privateMessage.getUserId()));
        if (ObjectUtils.isNotEmpty(privateMessage)) {
            if (ObjectUtils.isEmpty(robotFriend) || robotFriend.getStatus() == StateEnum.DISABLE.getType()) {
                log.error("该群聊已被禁用=>{}", robotFriend.getQq());
                return;
            }
            String msg =privateMessage.getMessage();
            BusinessInvokeVo invoke = getData(new MessageVo(msg, null, privateMessage.getUserId()));
            invoke.setMessageType(RobotMessageConstant.MessageType.PRIVATE);
            Method method = CommonUtil.getRobotClientMethod(invoke);
            CommonUtil.invokeRobotSend(method,robotClient,invoke,privateMessage.getUserId());
        }
    }
}
