package com.wheel.service.mq.config;

import com.wheel.service.mq.core.RmqSender;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description rockMq 自动注入配置
 * @author: zhouf
 * @date: 2020/6/3
 */
@ConditionalOnClass(RocketMQTemplate.class)
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@Configuration
public class RmqAutoConfig {

    @Bean
    @ConditionalOnBean(RocketMQTemplate.class)
    public RmqSender rmqSender(RocketMQTemplate rocketMQTemplate) {
        return new RmqSender(rocketMQTemplate);
    }
}
