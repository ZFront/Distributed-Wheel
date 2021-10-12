package com.wheel.service.redis.prop;

import com.wheel.common.annotation.Desc;
import com.wheel.common.constant.RedisModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "wheel.redis")
public class RedisProp {

    @Desc(value = "redis 单点地址")
    private String host = "localhost";

    @Desc(value = "redis 集群地址")
    private String cluster;

    @Desc(value = "redis 哨兵主")
    private String master;

    @Desc(value = "redis 哨兵从节点")
    private String nodes;

    @Desc(value = "redis 端口")
    private Integer port = 6379;

    @Desc(value = "redis 数据库")
    private Integer database = 0;

    @Desc(value = "redis 密码")
    private String password;

    @Desc(value = "redis 读取超时时间")
    private Long timeOut = 5000L;

    @Desc(value = "redis 最大连接数")
    private Integer maxActive = 200;

    @Desc(value = "redis 获取连接时的最大等待毫秒数")
    private Long maxWait = 1000L;

    @Desc(value = "redis 最大空闲连接数")
    private Integer maxIdle = 50;

    @Desc(value = "redis 最小空闲连接数")
    private Integer minIdle = 0;

    /**
     * 模式链接
     * {@link RedisModel}
     */
    @Desc(value = "redis 模式： 默认sentinel")
    private String redisModel = "sentinel";
}
