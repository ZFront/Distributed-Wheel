package com.wheel.web.api.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc 后台API请求拦截器
 * @author: zhouf
 */
@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("AuthInterceptor start ...");
        if (!(handler instanceof HandlerMethod)) {
            log.error("{}：handler type is not support !", handler.getClass().getName());
            return false;
        }
        return true;
    }
}
