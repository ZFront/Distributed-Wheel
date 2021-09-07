package com.wheel.unique.service;

import com.wheel.unique.biz.RedisIdBiz;
import com.wheel.unique.biz.SnowFlakeBiz;
import com.wheel.unique.biz.SqlIdBiz;
import com.wheel.unique.biz.UuidBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description
 * @author: zhouf
 * @date: 2020/7/30
 */
@Service
public class UniqueFacadeImpl implements UniqueFacade {

    @Autowired
    private RedisIdBiz redisIdBiz;

    @Autowired
    private SnowFlakeBiz snowFlakeBiz;

    @Autowired
    private SqlIdBiz sqlIdBiz;

    @Autowired
    private UuidBiz uuidBiz;

    @Override
    public long getSegmentId(String key) {
        return sqlIdBiz.getId(key);
    }

    @Override
    public List<Long> getSegmentId(String key, int count) {
        return sqlIdBiz.getId(key, count);
    }

    @Override
    public long getSnowFlakeId(String key) {
        return snowFlakeBiz.getId(key);
    }

    @Override
    public long getRedisId(String key) {
        return redisIdBiz.getId(key);
    }

    @Override
    public long getRedisId(String key, int incrStep) {
        return redisIdBiz.getId(key, incrStep);
    }

    @Override
    public String getUUID() {
        return uuidBiz.getId();
    }
}
