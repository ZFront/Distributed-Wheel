package com.wheel.db.sharding.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @desc
 * @author: zhouf
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.sharding")
public class ShardingDbProp {

    private String shardingMode = "";

}
