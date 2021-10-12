package com.wheel.service.lock.dao;

import com.wheel.facade.lock.entity.Locker;
import com.wheel.common.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: zhouf
 * @date: 2020/6/28
 */
@Repository
public class LockerDao extends MyBatisDao<Locker, Long> {

    public Locker getByResourceId(String resourceId) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("resourceId", resourceId);
        return getOne("getByResourceId", param);
    }
}
