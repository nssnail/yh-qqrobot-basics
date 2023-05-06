package com.yh.robot.handler.business.impl.chatgpt;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yh.robot.annotation.HandleService;
import com.yh.robot.client.RobotClient;
import com.yh.robot.enums.HandleType;
import com.yh.robot.enums.SendMsgType;
import com.yh.robot.handler.business.RobotBusinessHandler;
import com.yh.robot.handler.business.vo.MessageVo;
import com.yh.robot.redis.RedisClient;
import com.yh.robot.redis.RedisKey;
import com.yh.robot.third.chatgpt.ChatGPTClient;
import com.yh.robot.third.chatgpt.ChatGPTConfig;
import com.yh.robot.third.chatgpt.vo.SessionVo;
import com.yh.robot.util.CQCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月24日 10:40:00
 */
@Slf4j
@HandleService(value = "ChatGPTHandler"
        , handleType = HandleType.NOTHING
        , sendType = SendMsgType.TEXT
        , handleName = "ChatGPT处理器")
public class ChatGPTHandler implements RobotBusinessHandler {

    @Resource
    private RobotClient robotClient;
    @Resource
    private RedisClient client;
    @Resource
    protected ChatGPTConfig chatGPTConfig;
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public List<String> handle(MessageVo messageVo) {
        String tips = "正在为你查找答案，请稍后";
        if (ObjectUtils.isNotNull(messageVo.getGroupId(), messageVo.getUserId())) {
            String waiting = CQCodeUtil.buildAtCQCode(messageVo.getUserId()) + tips;
            robotClient.sendGroupMessage(waiting, messageVo.getGroupId());
        } else if (ObjectUtils.isEmpty(messageVo.getGroupId()) && ObjectUtils.isNotEmpty(messageVo.getUserId())) {
            robotClient.sendPrivateMessage(tips, messageVo.getUserId());
        }
        String userId = messageVo.getUserId().toString();
        String redisKey = RedisKey.CHAT_SESSION_ID.getKey(userId);
        String session = client.get(redisKey);
        SessionVo sessionVo = new SessionVo();
        if (StringUtils.isBlank(session)) {
            String sessionId = UUID.randomUUID().toString();
            List<String> apiKeys = chatGPTConfig.getApiKeys();
            int i = RandomUtil.randomInt(0, apiKeys.size());
            String key = apiKeys.get(i);
            sessionVo.setSessionId(sessionId);
            sessionVo.setSecretKey(key);
            client.set(redisKey, JSONObject.toJSONString(sessionVo), chatGPTConfig.getExpireTime());
        } else {
            sessionVo = JSONObject.parseObject(session,SessionVo.class);
            //如果不为空，则续session
            client.expire(redisKey, chatGPTConfig.getExpireTime());
        }
        log.info("chatgpt获取sessionkey-> {}", JSONObject.toJSONString(sessionVo));
        String beanName = chatGPTConfig.getUse().equals("real") ? "ChatGPTRealClient" : "ChatGPTProxyClient";
        ChatGPTClient chatGPTClient = applicationContext.getBean(beanName, ChatGPTClient.class);
        String msg = chatGPTClient.getText(messageVo.getMsg(), sessionVo.getSessionId(), sessionVo.getSecretKey());
        return Collections.singletonList(msg);
    }

}