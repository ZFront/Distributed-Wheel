package com.wheel.gateway.config;

import com.wheel.gateway.ratelimit.core.IpKeyResolver;
import com.wheel.gateway.ratelimit.core.UriKeyResolver;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @description 项目配置
 * @author: zhouf
 * @date: 2020/9/7
 */
@SpringBootConfiguration
public class GatewayConfig {

    @Bean(name = "ipKeyResolver")
    public IpKeyResolver ipKeyResolver() {
        return new IpKeyResolver();
    }

    @Bean(name = "uriKeyResolver")
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }
}
