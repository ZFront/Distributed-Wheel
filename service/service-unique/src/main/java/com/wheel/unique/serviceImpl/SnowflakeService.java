package com.wheel.unique.serviceImpl;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ZeroIDGen;
import com.sankuai.inf.leaf.snowflake.SnowflakeIDGenImpl;
import com.wheel.unique.config.LeafProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/28
 */
@Slf4j
@Service
public class SnowflakeService {

    private IDGen idGen;

    @Autowired
    LeafProp leafProp;

    @PostConstruct
    public void init() {
        boolean flag = leafProp.getSnowflake().getEnable();
        if (flag) {
            String zkAddress = leafProp.getSnowflake().getZkAddress();
            String leafName = leafProp.getSnowflake().getName();
            int port = leafProp.getSnowflake().getZkPort();
            idGen = new SnowflakeIDGenImpl(zkAddress, port, leafName);
            if (idGen.init()) {
                log.info("Snowflake Service Init Successfully");
            } else {
                throw new RuntimeException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}
