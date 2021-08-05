package com.wheel.demo.sharding.controller;

import com.wheel.common.util.StringUtil;
import com.wheel.common.vo.api.ResponseParamVo;
import com.wheel.demo.sharding.dao.OrderDao;
import com.wheel.demo.sharding.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc
 * @author: zhouf
 */
@RestController
@RequestMapping("/sharding/")
public class OrderController {

    private static String USER_ID = "USER_007";

    @Autowired
    private OrderDao orderDao;

    @GetMapping("batchInsert")
    public ResponseParamVo batchInsert() {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Order order = new Order();
            order.setUserId(USER_ID);
            order.setOrderNo("O_" + StringUtil.getUUIDStr());
            order.setTrxNo("T_" + StringUtil.getUUIDStr());
            order.setOrderAmount(BigDecimal.TEN);
            orderList.add(order);
        }
        orderDao.insert(orderList);
        return ResponseParamVo.success();
    }

    @GetMapping("query")
    public ResponseParamVo query() {
        Map<String, Object> params = new HashMap<>(2);
        params.put("userId", USER_ID);
        return ResponseParamVo.success(orderDao.listBy(params));
    }

}
