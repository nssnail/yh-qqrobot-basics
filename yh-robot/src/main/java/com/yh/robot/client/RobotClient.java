package com.yh.robot.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yh.robot.annotation.SendMsg;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.constant.SendApiConstant;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.message.result.GetFriendListResult;
import com.yh.robot.message.result.GetGroupResult;
import com.yh.robot.message.result.SendResult;
import com.yh.robot.message.send.SendGroupMessage;
import com.yh.robot.message.send.SendMessage;
import com.yh.robot.message.send.SendPrivateMessage;
import com.yh.robot.util.CQCodeUtil;
import com.yh.robot.util.HttpUtil;
import com.yh.robot.util.LengthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName RobotSendUtil
 * @Description TODO
 * @Author Y
 * @Date 2023/3/16 22:36
 * @Version 1.0
 */
@Component
@Slf4j
public class RobotClient {


    @Value("${postUrl}")
    private String url;

    private String getUrl(String action) {
        return url + action;
    }

    @SendMsg(type = SendMsgType.TEXT, messageType = RobotMessageConstant.MessageType.GROUP)
    public void sendGroupMessage(String msg, Long groupId) {
        log.info("[发送群消息]群id:{},数据长度：{}，msg:{}", groupId, msg.length(), msg);
        List<String> msgList = new ArrayList<>();
        if (LengthUtil.isOutOfTextLength(msg)) {
            msgList.add("消息超出QQ文本上限，下面将拆分文本发送");
            List<String> msgs = LengthUtil.split(msg);
            msgList.addAll(msgs);
        } else {
            msgList.add(msg);
        }
        msgList.forEach(m -> {
            singleSendGroupMessage(m, groupId);
            if (msgList.size() > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("线程睡眠失败",e);
                }
            }
        });
    }

    private void singleSendGroupMessage(String msg, Long groupId) {
        SendMessage sendMessage = new SendGroupMessage(groupId, msg);
        String result = HttpUtil.post(getUrl(sendMessage.getAction()), sendMessage.buildPostData());
        log.info("go-cqhttp请求接口返回=>{}：", result);
        SendResult sendResult = JSONObject.parseObject(result, SendResult.class);
        if (sendResult.getRetcode() == 100) {
            String errMsg = "";
            if (sendResult.getStatus().equals("failed")) {
                errMsg = "QQ服务器请求异常";
            }
            SendMessage errMessage = new SendGroupMessage(groupId, errMsg);
            HttpUtil.post(getUrl(sendMessage.getAction()), errMessage.buildPostData());
        }
    }

    @SendMsg(type = SendMsgType.IMAGE, messageType = RobotMessageConstant.MessageType.GROUP)
    public void sendGroupImageMessage(String msg, Long groupId) {
        String data = CQCodeUtil.buildImageCQCode(msg, "");
        sendGroupMessage(data, groupId);
    }

    @SendMsg(type = SendMsgType.VIDEO, messageType = RobotMessageConstant.MessageType.GROUP)
    public void sendGroupVideoMessage(String msg, Long groupId) {
        String data = CQCodeUtil.buildVideoCQCode(msg, "https://images.m.ofweek.com/Upload/News/2015-9/RB/17/276.jpg");
        sendGroupMessage(data, groupId);
    }


    @SendMsg(type = SendMsgType.TEXT, messageType = RobotMessageConstant.MessageType.PRIVATE)
    public void sendPrivateMessage(String msg, Long userId) {
        List<String> msgList = new ArrayList<>();
        if (LengthUtil.isOutOfTextLength(msg)) {
            log.info("[发送私聊消息]超出消息长度，即将拆分，长度：{}", msg.length());
            msgList.add("消息超出QQ文本上限，下面将拆分文本发送");
            List<String> msgs = LengthUtil.split(msg);
            msgList.addAll(msgs);
        } else {
            msgList.add(msg);
        }

        msgList.forEach(m -> {
            singleSendPrivateMessage(m, userId);
            if (msgList.size() > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("线程睡眠失败",e);
                }
            }
        });
    }

    private void singleSendPrivateMessage(String msg, Long userId) {
        log.info("[发送私聊消息]qq:{},数据长度：{},msg:{}", userId, msg.length(), msg);
        SendMessage sendMessage = new SendPrivateMessage(userId, msg);
        String result = HttpUtil.post(getUrl(sendMessage.getAction()), sendMessage.buildPostData());
        log.info("go-cqhttp请求接口返回=>{}：", result);
        SendResult sendResult = JSONObject.parseObject(result, SendResult.class);
        if (sendResult.getRetcode() == 100) {
            String errMsg = "";
            if (sendResult.getStatus().equals("failed")) {
                errMsg = "QQ服务器请求异常";
            }
            SendMessage errMessage = new SendPrivateMessage(userId, errMsg);
            HttpUtil.post(getUrl(sendMessage.getAction()), errMessage.buildPostData());
        }
    }

    @SendMsg(type = SendMsgType.IMAGE, messageType = RobotMessageConstant.MessageType.PRIVATE)
    public void sendPrivateImageMessage(String msg, Long userId) {
        String data = CQCodeUtil.buildImageCQCode(msg, "");
        sendPrivateMessage(data, userId);
    }

    @SendMsg(type = SendMsgType.VIDEO, messageType = RobotMessageConstant.MessageType.PRIVATE)
    public void sendPrivateVideoMessage(String msg, Long userId) {
        String data = CQCodeUtil.buildVideoCQCode(msg, "https://images.m.ofweek.com/Upload/News/2015-9/RB/17/276.jpg");
        sendPrivateMessage(data, userId);
    }

    public GetFriendListResult getFriendList() {
        return HttpUtil.get(getUrl(SendApiConstant.Common.GET_FRIEND_LIST), GetFriendListResult.class);
    }

    public GetGroupResult getGroupList() {
        return HttpUtil.get(getUrl(SendApiConstant.Common.GET_GROUP_LIST), GetGroupResult.class);
    }

}
