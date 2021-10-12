package com.wheel.service.mq.config;

import com.wheel.service.mq.monitor.AmqMonitorTask;
import com.wheel.service.mq.prop.AmqMonitorProp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @desc activeMQ 监控自动装载配置类
 * @author: zhouf
 */
@Slf4j
@SpringBootConfiguration
@EnableConfigurationProperties({AmqMonitorProp.class})
public class AmqMonitorConfig {

    @Autowired
    private AmqMonitorProp amqMonitorProp;

    @Bean
    @ConditionalOnProperty(name = "monitor.activemq.enabled", havingValue = "true")
    public AmqMonitorTask amqMonitorTask() {
        List<AmqMonitorProp.BrokerNode> brokerNodes = amqMonitorProp.getBrokerNodes();
        if (CollectionUtils.isEmpty(brokerNodes)) {
            log.info("activemq ==> 未配置需要监控的节点");
            return null;
        }
        return new AmqMonitorTask(amqMonitorProp);
    }
}