package com.wheel.web.api.common.config;

import com.wheel.web.api.common.interceptor.AuthInterceptor;
import com.wheel.web.api.common.interceptor.TokenInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @desc 统一网关 API 服务配置类
 * @author: zhouf
 */
@EnableConfigurationProperties(WebApiProp.class)
@Configuration
public class WebApiConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());
        registry.addInterceptor(new TokenInterceptor());
    }
}
