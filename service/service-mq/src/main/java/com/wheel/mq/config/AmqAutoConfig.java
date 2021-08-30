package com.wheel.mq.config;

import com.wheel.mq.core.AmqSender;
import com.wheel.mq.prop.MqProp;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.DeliveryMode;

/**
 * @description
 * @author: zhouf
 * @date: 2020/5/28
 */
@Configuration
@EnableJms
@EnableConfigurationProperties(MqProp.class)
public class AmqAutoConfig {

    @Autowired
    private MqProp mqProp;

    @Bean
    public ActiveMQConnectionFactory initConnectionFactory() {
        return new ActiveMQConnectionFactory(mqProp.getUser(), mqProp.getPassword(), mqProp.getBrokerUrl());
    }

    /**
     * 发queue都统一用这模版 持久化 自动确认
     *
     * @param connectionFactory
     * @return
     */
    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        //进行持久化配置 1表示非持久化，2表示持久化 默认
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

    /**
     * 发topic都统一用这个模拟 持久化 ack模式
     *
     * @param connectionFactory
     * @return
     */
    @Bean(name = "ackJmsTemplate")
    public JmsTemplate ackJmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        //进行持久化配置 1表示非持久化，2表示持久化 默认
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setConnectionFactory(connectionFactory);
        //客户端签收模式
        jmsTemplate.setSessionAcknowledgeMode(4);
        return jmsTemplate;
    }

    /**
     * 配置重发策略：
     * 失败后重发时间段为：1、2、4、8、16、32
     *
     * @return
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        // 是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        // 重试次数,默认为6次
        redeliveryPolicy.setMaximumRedeliveries(mqProp.getMaximumRedeliveries());
        // 首次重发间隔，默认1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        // 成倍数增长的时间
        redeliveryPolicy.setBackOffMultiplier(2);
        return redeliveryPolicy;
    }

    @Bean
    public AmqSender amqSender() {
        return new AmqSender();
    }
}
