package com.wheel.service.mq.config;

import com.wheel.service.mq.core.AmqSender;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * @description
 * @author: zhouf
 * @date: 2020/5/28
 */
@Configuration
@ConditionalOnClass(JmsTemplate.class)
@AutoConfigureAfter(ActiveMQAutoConfiguration.class)
public class AmqAutoConfig {

    @Bean
    public AmqSender amqSender(JmsTemplate jmsTemplate) {
        return new AmqSender(jmsTemplate);
    }
}
