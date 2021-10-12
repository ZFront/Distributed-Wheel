package com.wheel.service.redis.core;

import com.wheel.service.redis.prop.RedisProp;
import com.wheel.common.constant.RedisModel;
import com.wheel.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @description rids连接工厂
 * @author: zhouf
 * @date: 2020/6/1
 */
@Slf4j
public class RedisConnectionFactory {

    public static JedisConnectionFactory init(RedisProp redisProp) {
        // JedisPoolConfig 初始化
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(redisProp.getMinIdle());
        poolConfig.setMaxIdle(redisProp.getMaxIdle());
        poolConfig.setMaxWaitMillis(redisProp.getMaxWait());
        poolConfig.setMaxTotal(redisProp.getMaxActive());
        // 检查连接可用性
        poolConfig.setTestOnBorrow(true);

        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(poolConfig)
                .and()
                .readTimeout(Duration.ofMillis(redisProp.getTimeOut()))
                .build();

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        String redisModel = redisProp.getRedisModel();

        switch (redisModel) {
            case RedisModel.REDIS_SINGLE:
                RedisStandaloneConfiguration singleRedisConfig = getRedisSingleConfiguration(redisProp);
                connectionFactory = new JedisConnectionFactory(singleRedisConfig, clientConfig);
                break;
            case RedisModel.REDIS_CLUSTER:
                RedisClusterConfiguration clusterRedisConfig = getRedisClusterConfiguration(redisProp);
                connectionFactory = new JedisConnectionFactory(clusterRedisConfig, clientConfig);
                break;
            case RedisModel.REDIS_SENTINEL:
                RedisSentinelConfiguration sentinelRedisConfig = getRedisSentinelConfiguration(redisProp);
                connectionFactory = new JedisConnectionFactory(sentinelRedisConfig, clientConfig);
                break;
            default:
                log.error("初始化JedisConnectionFactory异常，请指定对应的模式{single,sentinel,cluster}");
        }
        log.info("初始化JedisConnectionFactory成功");
        return connectionFactory;
    }

    /**
     * 获取redis单点配置
     *
     * @param redisProp
     * @return
     */
    private static RedisStandaloneConfiguration getRedisSingleConfiguration(RedisProp redisProp) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisProp.getHost());
        if (!StringUtils.isEmpty(redisProp.getPassword())) {
            redisConfig.setPassword(RedisPassword.of(redisProp.getPassword()));
        }
        redisConfig.setPort(redisProp.getPort());
        redisConfig.setDatabase(redisProp.getDatabase());
        return redisConfig;
    }

    /**
     * 获取redis集群配置
     *
     * @param redisProp
     * @return
     */
    private static RedisClusterConfiguration getRedisClusterConfiguration(RedisProp redisProp) {

        if (StringUtils.isEmpty(redisProp.getCluster())) {
            throw new BizException("redis集群模式：集群地址不能为空");
        }

        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        List<RedisNode> nodeList = new ArrayList<>();
        //分割出集群节点
        splitNode(nodeList, redisProp.getCluster().split(","));
        redisConfig.setClusterNodes(nodeList);
        if (!StringUtils.isEmpty(redisProp.getPassword())) {
            redisConfig.setPassword(RedisPassword.of(redisProp.getPassword()));
        }
        return redisConfig;
    }

    /**
     * 获取redis哨兵配置
     *
     * @param redisProp
     * @return
     */
    private static RedisSentinelConfiguration getRedisSentinelConfiguration(RedisProp redisProp) {

        if (StringUtils.isEmpty(redisProp.getMaster()) || StringUtils.isEmpty(redisProp.getNodes())) {
            throw new BizException("redis哨兵模式：master or node不能为空");
        }

        RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
        redisConfig.setMaster(redisProp.getMaster());
        List<RedisNode> nodeList = new ArrayList<>();
        //分割出集群节点
        splitNode(nodeList, redisProp.getNodes().split(","));
        redisConfig.setSentinels(nodeList);
        if (!StringUtils.isEmpty(redisProp.getPassword())) {
            redisConfig.setPassword(RedisPassword.of(redisProp.getPassword()));
        }
        return redisConfig;
    }

    /**
     * 切割集群节点
     *
     * @param nodeList 节点列表
     * @param cNodes   节点
     */
    private static void splitNode(List<RedisNode> nodeList, String[] cNodes) {
        for (String node : cNodes) {
            String[] hp = node.split(":");
            nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }
    }
}
