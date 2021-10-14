package com.wheel.web.api.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @desc 配置类
 * @author: zhouf
 */
@Data
@ConfigurationProperties(prefix = "web.api")
public class WebApiProp {

    /**
     * 验证码方式, 1=图形验证码，2=短信验证码
     */
    private Integer codeType = 1;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * token 失效时间，单位秒
     */
    private Integer tokenExpiredSec = 30 * 60;

    /**
     * token 加密key
     */
    private String tokenSecretKey = "cb7e8a6bdb7226559cc65cb316512190";
}
