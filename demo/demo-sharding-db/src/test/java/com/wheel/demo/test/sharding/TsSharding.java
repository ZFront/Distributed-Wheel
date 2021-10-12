package com.wheel.demo.test.sharding;

import com.wheel.common.util.JsonUtil;
import com.wheel.common.util.RandomUtil;
import com.wheel.common.util.StringUtil;
import com.wheel.demo.test.sharding.dao.OrderDao;
import com.wheel.demo.test.sharding.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoShardingApplication.class)
public class TsSharding {


    private static String USER_ID = "USER_007";

    @Autowired
    private OrderDao orderDao;


    @Test
    public void testBatchInsert() {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setUserId(USER_ID);
            // 在实际应用场景中，肯定是有对应生成规则的，如：前缀+时间戳+后缀。在分库分表的时候，应对标识符做处理
            order.setOrderNo(RandomUtil.generateNum(6) + i);
            order.setTrxNo("T_" + StringUtil.getUUIDStr());
            order.setOrderAmount(BigDecimal.TEN);
            orderList.add(order);
        }
        orderDao.insert(orderList);
    }

    @Test
    public void query() {
        Map<String, Object> params = new HashMap<>(2);
        params.put("userId", USER_ID);
        System.out.println(JsonUtil.toString(orderDao.listBy(params)));
    }
}
