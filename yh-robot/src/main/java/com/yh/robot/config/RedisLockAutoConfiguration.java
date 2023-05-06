package com.yh.robot.config;

import com.yh.robot.redis.RedisLock;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

@ConditionalOnClass({Redisson.class, RedisOperations.class})
@Configuration
public class RedisLockAutoConfiguration {

    @Bean(destroyMethod = "destroy")
    public RedisLock redisLock(RedissonClient redisson) {
        return new RedisLock(redisson);
    }
}
