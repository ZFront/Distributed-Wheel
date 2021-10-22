package com.wheel.web.api.common.resolver;

import com.wheel.common.exception.BizException;
import com.wheel.common.util.StringUtil;
import com.wheel.web.api.common.annotation.CurrentUser;
import com.wheel.web.api.common.base.CacheBiz;
import com.wheel.web.api.common.constant.WebKey;
import com.wheel.web.api.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @desc 方法参数注入，将 Current 注解的方法参数注入当前登录的用户
 * @author: zhouf
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private CacheBiz cacheBiz;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 如果参数类型是 User 并且有 CurrentUser 注解则支持
        if (methodParameter.getParameterType().isAssignableFrom(User.class) &&
                methodParameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        // 获取 request 中存放的数据
        String loginName = (String) nativeWebRequest.getAttribute(WebKey.CURRENT_USER_NAME, RequestAttributes.SCOPE_REQUEST);
        if (StringUtil.isNotEmpty(loginName)) {
            User user = cacheBiz.getLoginUser(loginName);
            if (user == null) {
                throw new BizException("用户不存在");
            }
            return user;
        }
        throw new BizException("用户不存在");
    }
}
