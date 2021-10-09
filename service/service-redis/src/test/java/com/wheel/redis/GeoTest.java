package com.wheel.redis;

import com.wheel.redis.client.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc redis - geo 测试类
 * @author: zhouf
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceRedisApp.class)
public class GeoTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void testGeo() {
        Map<String, GeoCoordinate> map = new HashMap<>();
        // 添加各个点的位置
        map.put("A", new GeoCoordinate(116.404269, 39.913164));
        map.put("B", new GeoCoordinate(116.36, 39.922461));
        map.put("C", new GeoCoordinate(116.499705, 39.874635));
        map.put("D", new GeoCoordinate(116.193275, 39.996348));

        String key = "person";
        redisClient.geoadd(key, map);

        // 计算 A、C 的支线距离
        log.info("A、C之间相距：{} KM", redisClient.geodist(key, "A", "C", GeoUnit.KM));

        // 查找附近5公里的人
        List<GeoRadiusResponse> member = redisClient.georadiusByMember(key, "A", 10, GeoUnit.KM);

        for (GeoRadiusResponse response : member) {
            log.info("A 附近10公里的人有：{}", response.getMemberByString());
        }
    }
}
