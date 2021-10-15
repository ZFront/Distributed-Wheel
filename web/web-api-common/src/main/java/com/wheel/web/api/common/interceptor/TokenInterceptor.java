package com.wheel.web.api.common.interceptor;

import com.wheel.common.exception.BizException;
import com.wheel.web.api.common.constant.WebKey;
import com.wheel.web.api.common.kit.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        log.debug("TokenInterceptor start ...");

        String token = tokenManager.getTokenFromRequest(request);

        // 验证 token
        Map<String, String> claims = tokenManager.verifyAndRenewToken(token);
        if (claims == null) {
            throw new BizException("Token无效或已过期");
        }

        // 验证 token 与客户端是否匹配
        String userAgent = request.getHeader("User-Agent");
        if (tokenManager.verifyClientInfo(userAgent, claims)) {
            throw new BizException("客户端信息不匹配");
        }

        // 验证成功，保存至 request 中
        String loginName = tokenManager.getUserName(claims);
        request.setAttribute(WebKey.CURRENT_USER_NAME, loginName);
        return true;
    }
}
