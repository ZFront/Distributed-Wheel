package com.wheel.service.lock.core;

import com.wheel.common.constant.RedisModel;
import com.wheel.common.exception.BizException;
import com.wheel.service.lock.config.RedisProp;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.util.StringUtils;

/**
 * @desc redissonClient连接工厂
 * @author: zhouf
 */
@Slf4j
public class RedissonClientFactory {

    private static String URL_PREFIX = "redis://";

    public static RedissonClient init(RedisProp redisProp) {
        Config config = new Config();
        String redisModel = redisProp.getRedisModel();
        switch (redisModel) {
            case RedisModel.REDIS_SENTINEL:
                setRedisSentinelConfig(config, redisProp);
                break;
            case RedisModel.REDIS_CLUSTER:
                setRedisClusterConfig(config, redisProp);
                break;
            case RedisModel.REDIS_SINGLE:
                setRedisSingleConfig(config, redisProp);
                break;
            default:
                log.error("init RedissonClient，请指定对应的模式{single,sentinel,cluster}");
        }
        log.info("init RedissonClient成功");
        return Redisson.create(config);
    }

    private static void setRedisSentinelConfig(Config config, RedisProp redisProp) {
        if (StringUtils.isEmpty(redisProp.getMaster()) || StringUtils.isEmpty(redisProp.getNodes())) {
            throw new BizException("redis哨兵模式：master or node不能为空");
        }

        config.useSentinelServers()
                // 这里的节点需要以 redis:// 开头
                .addSentinelAddress(covert(redisProp.getNodes()))
                .setDatabase(redisProp.getDatabase())
                .setMasterName(redisProp.getMaster())
                .setConnectTimeout(redisProp.getTimeOut().intValue())
                .setPassword(redisProp.getPassword());
    }

    private static void setRedisClusterConfig(Config config, RedisProp redisProp) {
        if (StringUtils.isEmpty(redisProp.getCluster())) {
            throw new BizException("redis集群模式：集群地址不能为空");
        }
        config.useClusterServers()
                .addNodeAddress(covert(redisProp.getNodes()))
                .setConnectTimeout(redisProp.getTimeOut().intValue())
                .setPassword(redisProp.getPassword());
    }

    private static void setRedisSingleConfig(Config config, RedisProp redisProp) {
        config.useSingleServer()
                .setAddress(URL_PREFIX + redisProp.getHost() + ":" + redisProp.getPort())
                .setConnectTimeout(redisProp.getTimeOut().intValue())
                .setPassword(redisProp.getPassword())
                .setDatabase(redisProp.getDatabase());
    }

    private static String[] covert(String nodes) {
        String[] nodeList = nodes.split(",");
        String[] result = new String[nodeList.length];
        for (int i = 0; i < nodeList.length; i++) {
            String node = nodeList[i];
            if (!node.startsWith(URL_PREFIX)) {
                node = URL_PREFIX + node;
            }
            result[i] = (node);
        }
        return result;
    }
}