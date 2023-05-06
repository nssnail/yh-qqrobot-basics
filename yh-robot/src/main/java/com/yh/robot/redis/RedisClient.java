package com.yh.robot.redis;

import com.yh.robot.enums.RedisMode;
import com.yh.robot.util.JsonUtil;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.util.Pool;

import java.util.*;

/**
 * @ClassName RedisClient
 * @Description TODO
 * @Author Y
 * @Date 2023/3/19 0:19
 * @Version 1.0
 */
public class RedisClient {
    private static final int DEFAULT_CACHE_SECONDS = 60 * 1;// 单位秒 设置成一分钟
    private RedisMode redisMode;//预留
    private boolean isClusterMode;
    private JedisCluster jedisCluster;
    private Pool<Jedis> pool;

    public RedisClient(RedisMode redisMode, Pool<Jedis> pool, JedisCluster jedisCluster) {
        Assert.notNull(redisMode, "redisMode is null");

        this.redisMode = redisMode;
        if (RedisMode.CLUSTER.equals(this.redisMode)) {
            Assert.notNull(jedisCluster, "When redisMode is CLUSTER, jedisCluster can not be null");
            this.jedisCluster = jedisCluster;
            this.isClusterMode = true;
        } else {
            Assert.notNull(pool, "pool can not be null");
            this.pool = pool;
            this.isClusterMode = false;
        }
    }

    public String get(String key) {
        if (isClusterMode) {
            return jedisCluster.get(key);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.get(key);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public <T> T get(String key, Class<T> clz) {
        if (isClusterMode) {
            String value = jedisCluster.get(key);
            if (value != null) {
                return JsonUtil.toBean(value, clz);
            } else {
                return null;
            }
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                String value = jedis.get(key);
                if (value != null) {
                    return JsonUtil.toBean(value, clz);
                } else {
                    return null;
                }
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public boolean set(String key, String value) {
        if (isClusterMode) {
            jedisCluster.set(key, value);
            return true;
        } else {
            try (Jedis jedis = pool.getResource()) {
                jedis.set(key, value);
                return true;
            }
        }
    }

    public boolean set(String key, String value, int seconds) {
        if (seconds == 0) {  // 如果超时时间为0，则设置为默认一分钟
            seconds = DEFAULT_CACHE_SECONDS;
        }

        boolean isNoExpire = seconds <= -1;  // -1 表示用不超时

        if (isClusterMode) {
            if (isNoExpire) {
                jedisCluster.set(key, value);
            } else {
                jedisCluster.setex(key, seconds, value);
            }
            return true;
        } else {
            try (Jedis jedis = pool.getResource()) {
                if (isNoExpire) {
                    jedis.set(key, value);
                } else {
                    jedis.setex(key, seconds, value);
                }
                return true;
            }
        }
    }

    public <T> boolean set(String key, T value, int seconds) {
        if (seconds == 0) {// 如果超时时间为0，则设置为默认一分钟
            seconds = DEFAULT_CACHE_SECONDS;
        }

        boolean isNoExpire = seconds <= -1;//-1 表示用不超时
        if (isClusterMode) {
            if (isNoExpire) {
                jedisCluster.set(key, JsonUtil.toString(value));
            } else {
                jedisCluster.setex(key, seconds, JsonUtil.toString(value));
            }
            return true;
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                if (isNoExpire) {
                    jedis.set(key, JsonUtil.toString(value));
                } else {
                    jedis.setex(key, seconds, JsonUtil.toString(value));
                }
                return true;
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public long expire(String key, int seconds) {
        if (isClusterMode) {
            return jedisCluster.expire(key, seconds);
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.expire(key, seconds);
            }
        }
    }

    public String hget(String key, String field) {
        if (isClusterMode) {
            return jedisCluster.hget(key, field);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.hget(key, field);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public List<String> hmget(String key, String... fields) {
        if (isClusterMode) {
            return jedisCluster.hmget(key, fields);
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.hmget(key, fields);
            }
        }
    }

    public Map<String, String> hgetAll(String key) {
        if (isClusterMode) {
            return jedisCluster.hgetAll(key);
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.hgetAll(key);
            }
        }
    }

    public boolean hset(String key, String field, String value) {
        if (isClusterMode) {
            return jedisCluster.hset(key, field, value) >= 0;
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.hset(key, field, value) >= 0;
            }
        }
    }

    public long hset(String key, Map<String, String> hash) {
        if (isClusterMode) {
            return jedisCluster.hset(key, hash);
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.hset(key, hash);
            }
        }
    }

    public Set<String> hkeys(String key) {
        if (isClusterMode) {
            return jedisCluster.hkeys(key);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.hkeys(key);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public long hdel(String key, final String... field) {
        if (isClusterMode) {
            return jedisCluster.hdel(key, field);
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.hdel(key, field);
            }
        }
    }

    public boolean del(String key) {
        if (isClusterMode) {
            return jedisCluster.del(key) >= 0;
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.del(key) >= 0;
            }
        }
    }


    public Long hincr(String key, String field) {
        if (isClusterMode) {
            return jedisCluster.hincrBy(key, field, 1);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.hincrBy(key, field, 1);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public Long incr(String key) {
        if (isClusterMode) {
            return jedisCluster.incr(key);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.incr(key);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public Long incrBy(String key, int increment) {
        if (isClusterMode) {
            return jedisCluster.incrBy(key, increment);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.incrBy(key, increment);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public <T> T eval(String script, List<String> keys, String... params) {
        if (isClusterMode) {
            return (T) jedisCluster.eval(script, keys, Arrays.asList(params));
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return (T) jedis.eval(script, keys, Arrays.asList(params));
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    //zset
    public Long zadd(String key, double score, String member) {
        if (isClusterMode) {
            return jedisCluster.zadd(key, score, member);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.zadd(key, score, member);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public Set<String> zrange(String key, long start, long stop) {
        if (isClusterMode) {
            return jedisCluster.zrange(key, start, stop);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.zrange(key, start, stop);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public Set<Tuple> zrangeWithScore(String key, long start, long stop) {
        if (isClusterMode) {
            return jedisCluster.zrangeWithScores(key, start, stop);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.zrangeWithScores(key, start, stop);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        if (isClusterMode) {
            return jedisCluster.zrangeByScore(key, min, max);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.zrangeByScore(key, min, max);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public Set<String> zrevrangeByScoreLimit(String key, String max, String min, int offset, int count) {
        if (isClusterMode) {
            return jedisCluster.zrevrangeByScore(key, max, min, offset, count);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.zrevrangeByScore(key, max, min, offset, count);
            } finally {
                if (jedis != null) {
                    jedis.close();
                    ;
                }
            }
        }
    }

    public List<String> lrange(String key, long start, long stop) {
        if (isClusterMode) {
            return jedisCluster.lrange(key, start, stop);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.lrange(key, start, stop);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public long zrem(String key, String... member) {
        if (isClusterMode) {
            return jedisCluster.zrem(key, member);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.zrem(key, member);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    /**
     * 执行lua脚本，同时缓存脚本到redis里，减少网络开销
     *
     * @param script  lua脚本
     * @param hashKey 根据此值计算hash值
     * @param keys    KEYS集合
     * @param argv    ARGV集合
     * @return
     */
    public Object evalLua(String script, String hashKey, List<String> keys, List<String> argv) {
        if (isClusterMode) {
            return jedisCluster.evalsha(jedisCluster.scriptLoad(script, hashKey), keys, argv);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.evalsha(jedis.scriptLoad(script), keys, argv);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    public Object evalLua(String scriptHash, List<String> keys, List<String> argv) {
        if (isClusterMode) {
            return jedisCluster.evalsha(scriptHash, keys, argv);
        } else {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.evalsha(scriptHash, keys, argv);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }


    public Long resetLoopNum(String key, int incrStep, long maxValue) {
        String script = "local currValue = redis.call('incrby', KEYS[1], ARGV[1]); "
                + "if currValue > tonumber(ARGV[2]) then "
                + "redis.call('set', KEYS[1], 0); "
                + "return redis.call('incrby', KEYS[1], ARGV[1]) "
                + "else "
                + "return currValue "
                + "end";
        Long id = eval(script, Collections.singletonList(key), String.valueOf(incrStep), String.valueOf(maxValue));
        return id;
    }

    public void destroy() {
        if (jedisCluster != null) {
            try {
                jedisCluster.close();
            } catch (Exception e) {

            }
        }
        if (pool != null) {
            try {
                pool.destroy();
            } catch (Exception e) {

            }
        }
    }

    public long lpush(String key, String... field) {
        if (isClusterMode) {
            return jedisCluster.lpush(key, field);
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.lpush(key, field);
            }
        }
    }

    public String lpop(String key) {
        if (isClusterMode) {
            return jedisCluster.lpop(key);
        } else {
            try (Jedis jedis = pool.getResource()) {
                return jedis.lpop(key);
            }

        }
    }
}