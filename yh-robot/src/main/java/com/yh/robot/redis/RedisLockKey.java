package com.yh.robot.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName RedisKeyConstant
 * @Description TODO
 * @Author Y
 * @Date 2023/3/19 0:33
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum RedisLockKey {

    MESSAGE("messageLock","消息锁");

    private final String key;

    private final String name;

    public String getKey(String value){
        return this.getKey()+":"+value;
    }

}
