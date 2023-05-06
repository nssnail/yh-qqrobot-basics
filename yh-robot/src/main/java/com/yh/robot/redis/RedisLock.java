package com.yh.robot.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisLock
 * @Description TODO
 * @Author Y
 * @Date 2023/3/19 0:25
 * @Version 1.0
 */
@Slf4j
public class RedisLock {
    private final RedissonClient client;
    private final ConcurrentHashMap<String, RLock> longLockMap = new ConcurrentHashMap<>();

    public RedisLock(RedissonClient client){
        if(client == null){
            throw new RuntimeException("RedissonClient 不能为null");
        }
        this.client = client;
    }

    public String getLockNamePrefix() {
        return "lock-";
    }

    public RedissonClient getClient(){
        return client;
    }


    public boolean isLock(String lockName){
        lockName = getRealLockName(lockName);
        RLock lock = getClient().getLock(lockName);
        return lock.isLocked();
    }

    public RLock tryLock(String lockName, int waitMills, long expireMills){
        lockName = getRealLockName(lockName);
        RLock lock = getClient().getLock(lockName);
        try{
            if(lock.tryLock(waitMills, expireMills, TimeUnit.MILLISECONDS)){
                return lock;
            }
        }catch(Throwable e){
            log.error("lockName={} 获取锁时出现异常", lockName, e);
        }
        return null;
    }


    public void unlock(List<RLock> lockList){
        if(lockList == null || lockList.isEmpty()){
            return;
        }

        for(RLock lock : lockList){
            try{
                unlock(lock);
            }catch(Throwable t){
                log.error("释放锁异常", t);
            }
        }
    }

    public void unlock(RLock lock) throws RuntimeException {
        try{
            lock.unlock();
        }catch(Throwable t){
            throw new RuntimeException("lockName = "+lock.getName()+" 释放锁时出现异常", t);
        }
    }


    /**
     * 强行释放锁，不管释放锁的线程是不是跟加锁时的线程一样，都可以释放锁
     * @param lock
     * @return
     */
    public void forceUnlock(RLock lock){
        try{
            lock.forceUnlock();
        }catch(Throwable t){
            log.error("lockName = {} 强制释放锁时出现异常", lock.getName(), t);
        }
    }

    /**
     * 在应用关闭前释放长时间锁
     */
    public void destroy(){
        if(client != null){
            try{
                //waiting for rpc shutdown
                Thread.sleep(2000);
            }catch(Exception e){}

            for(Map.Entry<String, RLock> entry : longLockMap.entrySet()){
                log.info("lockName={} 应用关闭前强制释放锁", entry.getKey());
                forceUnlock(entry.getValue());
            }
            client.shutdown(3, 7, TimeUnit.SECONDS);
        }
    }

    private String getRealLockName(String lockName){
        return getLockNamePrefix() + lockName;
    }
}
