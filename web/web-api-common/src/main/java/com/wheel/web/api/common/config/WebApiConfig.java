package com.wheel.web.api.common.config;

import com.wheel.web.api.common.interceptor.AuthInterceptor;
import com.wheel.web.api.common.interceptor.PermissionInterceptor;
import com.wheel.web.api.common.interceptor.TokenInterceptor;
import com.wheel.web.api.common.resolver.CurrentUserMethodArgumentResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @desc 统一网关 API 服务配置类
 * @author: zhouf
 */
@EnableConfigurationProperties(WebApiProp.class)
@Configuration
public class WebApiConfig implements WebMvcConfigurer {

    /**
     * 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());
        registry.addInterceptor(new TokenInterceptor());
        registry.addInterceptor(new PermissionInterceptor());
    }

    /**
     * 配置参数解析
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver());
    }

    /**
     *
     * @return
     */
    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }
}
