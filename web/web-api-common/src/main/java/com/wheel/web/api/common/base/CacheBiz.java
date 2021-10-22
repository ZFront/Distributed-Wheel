package com.wheel.web.api.common.base;

import com.wheel.web.api.common.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @desc 缓存业务类
 * @author: zhouf
 */
@Component
public class CacheBiz {

    @Cacheable(value = "redisCache", key = "targetClass + '.' + methodName + '.' + #userName")
    public User getLoginUser(String userName) {
        // 这里可以调用服务，但为了测试方便，直接返回一个
        User user = new User();
        user.setUserName(userName);
        user.setPhone("18888888888");
        user.setStatus(1);
        return user;
    }
}
