package com.yh.robot.constant;

import com.yh.robot.handler.message.RobotMessageHandler;
import com.yh.robot.handler.message.impl.GroupMessageHandler;
import com.yh.robot.handler.message.impl.PrivateMessageHandler;
import com.yh.robot.message.receive.GroupMessage;
import com.yh.robot.message.receive.PrivateMessage;
import com.yh.robot.message.receive.RealMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

public class RobotMessageConstant {


    @Getter
    @AllArgsConstructor
    public enum MessageType {

        GROUP("group", "群消息", GroupMessage.class, GroupMessageHandler.class),
        PRIVATE("private", "私聊消息", PrivateMessage.class, PrivateMessageHandler.class);

        private final String name;
        private final String desc;
        private final Class<? extends RealMessage> messageType;
        private final Class<? extends RobotMessageHandler> handlerType;

        public static MessageType getMessageType(String name){
            return Arrays.stream(values()).filter(p -> p.name.equals(name)).
                    findFirst().
                    orElse(null);
        }
    }

    public static class PostType {
        //普通消息
        public static final String MESSAGE = "message";
        //心跳消息
        public static final String META_EVENT = "meta_event";
        //通知消息
        public static final String NOTICE = "notice";
        //请求消息
        public static final String REQUEST = "request";
    }

    public static class RequestType {
        //好友
        public static final String FRIEND = "friend";
        //群
        public static final String GROUP = "group";
    }

    public static class NoticeType {
        //好友添加
        public static final String FRIEND_ADD = "friend_add";
        //群添加
        public static final String GROUP_INCREASE = "group_increase";
    }
}