package com.wheel.demo.test.sharding.dao;

import com.wheel.common.dao.MyBatisDao;
import com.wheel.demo.test.sharding.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 订单dao
 * @author: zhouf
 */
@Repository
public class OrderDao extends MyBatisDao<Order, Long> {

    public Order getByTrxNo(String trxNo) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("trxNo", trxNo);
        return getOne(param);
    }
}
