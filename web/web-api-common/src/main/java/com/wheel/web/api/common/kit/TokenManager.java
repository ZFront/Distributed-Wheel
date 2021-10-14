package com.wheel.web.api.common.kit;

import com.google.common.collect.Maps;
import com.wheel.common.util.JWTUtil;
import com.wheel.service.redis.client.RedisClient;
import com.wheel.web.api.common.config.WebApiProp;
import com.wheel.web.api.common.constant.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @desc token 管理器
 * @author: zhouf
 */
@Component
public class TokenManager {

    private static final String USER_AGENT = "userAgent";

    @Autowired
    private WebApiProp webApiProp;

    @Autowired
    private RedisClient redisClient;

    /**
     * 生成 token ，主要用于登录后生成
     *
     * @param loginName 登录名
     * @param ip        ip 地址
     * @param agent     浏览器代理
     * @return
     */
    public String createToken(String loginName, String ip, String agent) {
        // 1.先删除旧的 token
        deleteToken(loginName);

        Integer tokenExpiredSec = webApiProp.getTokenExpiredSec();
        // 2.生成token
        Map<String, String> claims = Maps.newHashMap();
        claims.put("USER_AGENT", agent);

        String token = JWTUtil.createToken(loginName, ip, tokenExpiredSec, webApiProp.getTokenSecretKey(), claims);

        // 3.保存token
        redisClient.setex(getKey(loginName), token, tokenExpiredSec);

        return token;
    }

    public String getTokenFromRequest(HttpServletRequest request) {

        return "";
    }

    /**
     * 校验 token 有效性，并刷新在服务端的过期时间
     *
     * @return
     */
    public boolean verifyAndRenewToken(String token) {
        // TODO 校验token本身是否有效


        return false;
    }

    public void deleteToken(String loginName) {
        redisClient.del(getKey(loginName));
    }

    public String getKey(String loginName) {
        return CacheKey.LOGIN_TOKEN_FLAG + webApiProp.getAppName() + loginName;
    }
}
