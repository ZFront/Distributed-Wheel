package com.wheel.web.api.common.kit;

import com.google.common.collect.Maps;
import com.wheel.common.exception.BizException;
import com.wheel.common.util.JWTUtil;
import com.wheel.common.util.StringUtil;
import com.wheel.service.redis.client.RedisClient;
import com.wheel.web.api.common.config.WebApiProp;
import com.wheel.web.api.common.constant.CacheKey;
import com.wheel.web.api.common.constant.WebKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @desc token 管理器
 * @author: zhouf
 */
@Slf4j
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
        redisClient.del(getKey(loginName));

        Integer tokenExpiredSec = webApiProp.getTokenExpiredSec();
        // 2.生成token
        Map<String, String> claims = Maps.newHashMap();
        claims.put(USER_AGENT, agent);

        String token = JWTUtil.createToken(loginName, ip, tokenExpiredSec, webApiProp.getTokenSecretKey(), claims);

        // 3.保存token
        redisClient.setex(getKey(loginName), token, tokenExpiredSec);

        return token;
    }

    /**
     * 从 HttpServletRequest 中获取 token信息
     *
     * @param request
     * @return
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String methodType = request.getMethod();
        String token = request.getHeader(WebKey.HTTP_TOKEN_HEADER);
        if (StringUtil.isEmpty(token)) {
            log.error("uri={}, methodType={} Token不能为空", uri, methodType);
            throw new BizException("Token头不能为空");
        }
        return token;
    }

    /**
     * 校验 token 有效性，并刷新在服务端的过期时间
     * 如果 token 有效，则返回对应的附加信息
     * 无效，则返回 null
     *
     * @return
     */
    public Map<String, String> verifyAndRenewToken(String token) {
        Map<String, String> claims;
        try {
            claims = JWTUtil.verifyToken(token, webApiProp.getTokenSecretKey());
        } catch (Exception e) {
            log.error("token 验证失败，token={}", token);
            return null;
        }
        // 校验 token 是否仍生效
        String loginName = JWTUtil.getSubject(claims);
        String currentToken = redisClient.get(loginName);
        if (StringUtil.isEmpty(currentToken)) {
            log.error("loginName:{}, 会话超时", loginName);
            return null;
        }
        if (!currentToken.equals(token)) {
            log.error("loginName:{}, 已在其他地方登录", loginName);
            return null;
        }

        // 延长在服务器的时间
        redisClient.setex(getKey(loginName), token, webApiProp.getTokenExpiredSec());
        return claims;
    }

    /**
     * 校验客户端信息
     *
     * @param userAgent
     * @param claims
     * @return
     */
    public boolean verifyClientInfo(String userAgent, Map<String, String> claims) {
        String loginName = JWTUtil.getSubject(claims);
        String agent = claims.get(USER_AGENT);
        if (agent == null || !agent.equals(userAgent)) {
            log.error("loginName:{} 与登录时的客户端不一致", loginName);
            return false;
        }
        return true;
    }

    /**
     * 获取当前用户名
     *
     * @param claims
     * @return
     */
    public String getUserName(Map<String, String> claims) {
        return JWTUtil.getSubject(claims);
    }

    public String getKey(String loginName) {
        return CacheKey.LOGIN_TOKEN_FLAG + webApiProp.getAppName() + loginName;
    }
}
