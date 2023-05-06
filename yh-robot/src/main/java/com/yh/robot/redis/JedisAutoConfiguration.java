package com.yh.robot.redis;

import com.yh.robot.enums.RedisMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@ConditionalOnClass({Jedis.class})
@EnableConfigurationProperties(JedisProperties.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
@Slf4j
public class JedisAutoConfiguration {

    @Autowired
    private JedisProperties properties;

    @ConditionalOnMissingBean(RedisClient.class)
    @Bean(destroyMethod = "destroy")
    public RedisClient redisClient() {
        if (properties.getCluster() != null) {
            return new RedisClient(RedisMode.CLUSTER, null, jedisCluster());
        } else if (properties.getSentinel() != null) {
            return new RedisClient(RedisMode.SENTINEL, jedisSentinelPool(), null);
        } else {
            return new RedisClient(RedisMode.STANDALONE, jedisStandalonePool(), null);
        }
    }

    private JedisCluster jedisCluster() {
        JedisProperties.ClusterConfig clusterConfig = properties.getCluster();
        Assert.notNull(clusterConfig, "cluster is null");

        List<String> list = clusterConfig.getNodes();
        Set<HostAndPort> nodes = new HashSet<>();
        for (String node : list) {
            log.info("add redis cluster node:{}", node);
            String[] arr = node.split("\\:");
            nodes.add(new HostAndPort(arr[0], Integer.parseInt(arr[1])));
        }
        int timeout = properties.getTimeout();
        int maxAttempts = clusterConfig.getMaxRedirects();
        JedisCluster jc = new JedisCluster(nodes,
                timeout, timeout, maxAttempts, properties.getPassword(), buildJedisPoolConfig(properties.getPool()));
        return jc;
    }


    private JedisSentinelPool jedisSentinelPool() {
        JedisProperties.SentinelConfig sentinelConfig = properties.getSentinel();
        Assert.notNull(sentinelConfig, "sentinel is null");

        String masterName = sentinelConfig.getMaster();
        //nodes
        Set<String> nodes = new HashSet<>();
        nodes.addAll(sentinelConfig.getNodes());

        int timeout = properties.getTimeout();
        JedisSentinelPool pool = new JedisSentinelPool(masterName, nodes,
                buildJedisPoolConfig(properties.getPool()), timeout, properties.getPassword(), properties.getDatabase());
        return pool;
    }

    private JedisPool jedisStandalonePool() {
        int timeout = properties.getTimeout();
        JedisPool pool = new JedisPool(buildJedisPoolConfig(properties.getPool()), properties.getHost(), properties.getPort(),
                timeout, properties.getPassword(), properties.getDatabase());
        return pool;
    }

    private JedisPoolConfig buildJedisPoolConfig(JedisProperties.PoolConfig config) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(config.getMaxTotal());
        poolConfig.setMaxIdle(config.getMaxIdle());
        poolConfig.setMinIdle(config.getMinIdle());

        poolConfig.setTestWhileIdle(config.isTestWhileIdle());
        poolConfig.setTestOnBorrow(config.isTestOnBorrow());
        poolConfig.setTestOnReturn(config.isTestOnReturn());

        poolConfig.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
        return poolConfig;
    }

}
