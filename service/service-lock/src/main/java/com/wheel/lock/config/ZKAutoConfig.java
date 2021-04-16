package com.wheel.lock.config;

import com.wheel.common.exception.BizException;
import com.wheel.common.util.StringUtil;
import com.wheel.lock.client.ZKClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc zk 自动装载
 * @author: zhouf
 */
@ConditionalOnClass(CuratorFramework.class)
@EnableConfigurationProperties(ZKProp.class)
@Configuration
public class ZKAutoConfig {

    @Autowired
    private ZKProp zkProp;

    @Bean
    public CuratorFramework initCurator() {
        if (StringUtil.isEmpty(zkProp.getZkUrl())) {
            throw new BizException("initCurator失败, zkUrl不能为空");
        }
        // 配置重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkProp.getBaseSleepTimeMs(), zkProp.getMaxRetries());
        return CuratorFrameworkFactory.builder()
                .connectString(zkProp.getZkUrl())
                .retryPolicy(retryPolicy)
                .namespace(zkProp.getNamespace())
                .sessionTimeoutMs(zkProp.getSessionTimeoutMs())
                .build();
    }

    @Bean
    public ZKClient initClient(CuratorFramework curator) {
        return new ZKClient(curator);
    }
}
