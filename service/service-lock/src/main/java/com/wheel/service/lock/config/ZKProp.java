package com.wheel.service.lock.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @desc zk客户端配置
 * @author: zhouf
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "zk")
public class ZKProp {

    /**
     * 失败重试间隔时间, 单位ms;
     */
    private int baseSleepTimeMs = 5 * 1000;
    /**
     * 失败重试次数
     */
    private int maxRetries = 3;

    /**
     * 会话存活时间
     */
    private int sessionTimeoutMs = 60 * 6 * 1000;

    /**
     * zk 地址
     */
    private String zkUrl = "";

    /**
     * 工作空间
     * 可以不指定，之后创建的所有节点都会在该工作空间下面，方便管理
     */
    private String namespace = "zk-locks";
}
