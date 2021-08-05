package com.wheel.db.sharding.config;

import com.wheel.db.sharding.prop.ShardingDbProp;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @desc
 * @author: zhouf
 */
@ConditionalOnClass(SpringBootShardingRuleConfigurationProperties.class)
@AutoConfigureBefore(SpringBootConfiguration.class)
@EnableConfigurationProperties(ShardingDbProp.class)
@Configuration
public class ShardingDbAutoConfig {
    
}

