package com.yh.robot.third.chatgpt;

import com.yh.robot.redis.RedisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年04月19日 11:28:00
 */
@Service
public abstract class ChatGPTClient {

    @Resource
    protected ChatGPTConfig chatGPTConfig;
    @Resource
    protected RedisClient client;

    public abstract String getText(String msg, String sessionId,String secretKey);

}
