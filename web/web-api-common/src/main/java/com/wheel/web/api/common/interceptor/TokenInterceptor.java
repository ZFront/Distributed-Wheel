package com.wheel.web.api.common.interceptor;

import com.wheel.web.api.common.kit.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc token 拦截器，校验 token 是否有效
 * 校验通过，则会往 HttpServletRequest 里面设置相关值
 * @author: zhouf
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("TokenInterceptor start ...");

        String token = tokenManager.getTokenFromRequest(request);

        // 验证token



        return true;
    }
}
