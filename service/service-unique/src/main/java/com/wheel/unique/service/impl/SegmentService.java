package com.wheel.unique.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ZeroIDGen;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.dao.IDAllocDao;
import com.sankuai.inf.leaf.segment.dao.impl.IDAllocDaoImpl;
import com.wheel.unique.config.LeafProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

/**
 * @description segment
 * @author: zhouf
 * @date: 2020/7/28
 */
@Slf4j
@Service
public class SegmentService {

    private IDGen idGen;

    private DruidDataSource dataSource;

    @Autowired
    LeafProp leafProp;

    @PostConstruct
    public void init() throws SQLException {
        boolean flag = leafProp.getSegment().getEnable();
        if (flag) {
            // Config dataSource
            dataSource = new DruidDataSource();
            dataSource.setUrl(leafProp.getSegment().getJdbcUrl());
            dataSource.setUsername(leafProp.getSegment().getJdbcUsername());
            dataSource.setPassword(leafProp.getSegment().getJdbcPassword());
            dataSource.init();

            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(dataSource);

            // Config ID Gen
            idGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) idGen).setDao(dao);
            if (idGen.init()) {
                log.info("Segment Service Init Successfully");
            } else {
                throw new RuntimeException("Segment Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}
