package com.yh.robot.handler.business.impl.chatgpt;

import com.yh.robot.annotation.HandleService;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.redis.RedisClient;
import com.yh.robot.redis.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@HandleService(value = "ChatGPTCancelHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.TEXT
        , handleName = "ChatGPT关闭session会话"
        , isApi = false)
@Slf4j
public class ChatGPTCancelHandler implements RobotBusinessHandler {

    @Resource
    private RedisClient client;

    @Override
    public List<String> handle(MessageVo messageVo) {
        log.info("关闭会话=>{}，=>{}", messageVo.getUserId(), messageVo.getGroupId());
        boolean set = client.del(RedisKey.CHAT_SESSION_ID.getKey(messageVo.getUserId().toString()));
        String str = set ? "关闭成功" : "关闭失败";
        return Collections.singletonList(str);
    }

}