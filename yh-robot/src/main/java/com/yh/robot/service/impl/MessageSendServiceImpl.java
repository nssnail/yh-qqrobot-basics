package com.yh.robot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.robot.constant.BeanNameConstant;
import com.yh.robot.constant.RobotMessageConstant;
import com.yh.robot.handler.message.RobotMessageDispatcher;
import com.yh.robot.handler.message.RobotMessageHandler;
import com.yh.robot.message.receive.RealMessage;
import com.yh.robot.message.result.RobotResult;
import com.yh.robot.redis.RedisClient;
import com.yh.robot.redis.RedisKey;
import com.yh.robot.redis.RedisLock;
import com.yh.robot.redis.RedisLockKey;
import com.yh.robot.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author nssnail
 * @Description TODO
 * @createTime 2023年03月16日 17:38:00
 */
@Service
@Slf4j
public class MessageSendServiceImpl implements MessageSendService {

    @Resource
    private RedisLock redisLock;
    @Resource
    private RedisClient redisClient;
    @Resource
    private ApplicationContext applicationContext;

    @Override
    @Transactional
    public RobotResult sendMessage(JSONObject jsonObject) {
        String postType = jsonObject.getString("post_type");
        log.info("接收消息：{}", JSONObject.toJSON(jsonObject));
        RobotMessageDispatcher dispatcher = applicationContext.getBean(BeanNameConstant.PREFIX + postType, RobotMessageDispatcher.class);
        if(ObjectUtil.isEmpty(dispatcher)){
            return RobotResult.success();
        }
        return dispatcher.dispatcher(jsonObject);
    }

    @Override
    public void handle(JSONObject jsonObject){
        String lockKey = RedisLockKey.MESSAGE.getKey(jsonObject.get("message_id").toString());
        String unionKey = RedisKey.MESSAGE.getKey(jsonObject.get("message_id").toString());
        log.info("redis锁：{},幂等键：{}", lockKey, unionKey);
        RLock rLock = redisLock.tryLock(lockKey, 60000, 60000);
        try {
            if (rLock == null) {
                log.error("获取锁失败");
            }
            String value = redisClient.get(unionKey);
            if (StringUtils.isBlank(value)) {
                redisClient.set(unionKey, "0", 60 * 5);
                RobotMessageConstant.MessageType messageType = RobotMessageConstant.MessageType.getMessageType(jsonObject.getString("message_type"));
                RealMessage message = JSONObject.parseObject(jsonObject.toJSONString(), messageType.getMessageType());
                RobotMessageHandler bean = SpringUtil.getBean(messageType.getHandlerType());
                bean.handler(message);
            } else {
                log.error("消息重复，跳过,key:{}", unionKey);
            }
        } finally {
            redisLock.unlock(rLock);
        }
    }

}