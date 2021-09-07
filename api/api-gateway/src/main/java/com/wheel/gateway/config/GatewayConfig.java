package com.wheel.gateway.config;

import com.wheel.gateway.filters.local.IPCheckGatewayFilterFactory;
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

    @Bean
    public IPCheckGatewayFilterFactory iPCheckGatewayFilterFactory() {
        return new IPCheckGatewayFilterFactory();
    }

    /**
     * ip限流器
     */
    @Bean
    public IpKeyResolver ipKeyResolver() {
        return new IpKeyResolver();
    }

    @Bean
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }
}
