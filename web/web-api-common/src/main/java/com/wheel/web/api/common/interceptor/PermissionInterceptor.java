package com.wheel.web.api.common.interceptor;

import com.wheel.web.api.common.annotation.NotAuth;
import com.wheel.web.api.common.annotation.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @desc 后台API请求拦截器
 * @author: zhouf
 */
@Slf4j
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("AuthInterceptor start ...");
        if (!(handler instanceof HandlerMethod)) {
            log.error("{}：handler type is not support !", handler.getClass().getName());
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Permission permissionTag = handlerMethod.getMethodAnnotation(Permission.class);
        // 为空，说明不需要权限控制
        if (permissionTag == null) {
            return true;
        }
        String authValue = permissionTag.value();

        // TODO 查询对应的权限，这里不做扩展
        String userName = "";
        List<String> userAuth = Arrays.asList("A", "B", "C");


        return true;
    }
}
