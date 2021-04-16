package com.wheel.mq.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description
 * @author: zhouf
 * @date: 2020/6/2
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "wheel.mq")
public class MqProp {

    /**
     * mq broker 地址
     */
    private String brokerUrl = "";

    /**
     * user
     */
    private String user = "";

    /**
     * pwd
     */
    private String password = "";

    /**
     * 最大重试次数
     */
    private int MaximumRedeliveries = 6;
}
