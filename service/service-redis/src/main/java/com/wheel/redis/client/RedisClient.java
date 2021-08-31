package com.wheel.redis.client;

import com.wheel.common.exception.BizException;
import com.wheel.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @description redis操作类
 * 目前只支持哨兵模式，暂不支持集群模式
 * {@link https://wizardforcel.gitbooks.io/redis-doc/content/ref/15.html} 相关操作说明文档
 * @author: zhouf
 * @date: 2020/6/1
 */
@Slf4j
public class RedisClient {

    /**
     * Redis 指令操作成功后返回的标识
     */
    private static final String STATUS_OK = "OK";

    private JedisConnectionFactory connectionFactory;


    public RedisClient(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private JedisConnection getJedisConnection() {
        return (JedisConnection) connectionFactory.getConnection();
    }

    // ========================= Key 操作 start ==============================

    /**
     * 判断key键是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        JedisConnection conn = getJedisConnection();
        try {
            return key == null ? false : conn.getJedis().exists(key);
        } finally {
            conn.close();
        }
    }

    public boolean del(String key) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().del(key) >= 0;
        } finally {
            conn.close();
        }
    }

    // ========================= Key 操作 end ==============================

    // ========================= String 操作 start ==============================

    /**
     * 获取Key的值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().get(key);
        } finally {
            conn.close();
        }
    }

    /**
     * 获取ket的值并转换
     *
     * @param key
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clz) {
        JedisConnection conn = getJedisConnection();
        try {
            return JsonUtil.toBean(conn.getJedis().get(key), clz);
        } finally {
            conn.close();
        }
    }

    /**
     * 设置set值
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        JedisConnection conn = getJedisConnection();
        try {
            return STATUS_OK.equals(conn.getJedis().set(key, value));
        } finally {
            conn.close();
        }
    }

    /**
     * 设置value超时
     * redis 服务端 ： -1：永不超时; 0:1分钟；
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public <T> boolean setex(String key, T value, int seconds) {
        String val = value instanceof String ? (String) value : JsonUtil.toString(value);
        JedisConnection conn = getJedisConnection();
        try {
            return STATUS_OK.equals(conn.getJedis().setex(key, seconds, val));
        } finally {
            conn.close();
        }
    }

    public long incrBy(String key, int incrStep) {
        JedisConnection conn = getJedisConnection();
        try {
            Long id = conn.getJedis().incrBy(key, incrStep);
            return id;
        } finally {
            conn.close();
        }
    }
    // ========================= String 操作 end ==============================

    // ========================= Hash 操作 start ==============================

    /**
     * redis服务端指令：
     * hset 成功返回1；如果哈希表中field已经存在，且旧值已被覆盖，则返回0
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public boolean hset(String key, String field, String value) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().hset(key, field, value) >= 0;
        } finally {
            conn.close();
        }
    }

    public String hget(String key, String field) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().hget(key, field);
        } finally {
            conn.close();
        }
    }

    public boolean hdel(String key, String field) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().hdel(key, field) >= 0;
        } finally {
            conn.close();
        }
    }

    // ========================= Hash 操作 end ==============================

    // ========================= 扩展写法 start ==============================

    /**
     * 获得循环的递增Id
     * 当获取的id > 最大值，则重置
     *
     * @param key      key值
     * @param incrStep 步长
     * @param maxValue 最大值
     * @return
     */
    public long loopIncrId(String key, int incrStep, long maxValue) {
        if (incrStep > maxValue) {
            throw new BizException("incrStep=" + incrStep + ",cannot bigger than maxValue=" + maxValue);
        }

        Long id = incrBy(key, incrStep);
        if (id > maxValue) {
            id = resetLoopNum(key, incrStep, maxValue);
        }
        return id;
    }

    /**
     * 重置num
     *
     * @param key
     * @param incrStep
     * @param maxValue
     * @return
     */
    public Long resetLoopNum(String key, int incrStep, long maxValue) {
        // redis中值大于某个值重置的lua脚本语句
        String script = "" +
                "local maxValue = tonumber(ARGV[2]);" +
                "local currValue = tonumber(redis.call('incrby', KEYS[1], ARGV[1]));" +
                "if currValue > maxValue then " +
                "  redis.call('set', KEYS[1], 0);" +
                "  currValue = tonumber(redis.call('incrby', KEYS[1], ARGV[1]));" +
                "end;" +
                "return currValue;";
        List<String> args = new ArrayList<>();
        args.add(String.valueOf(incrStep));
        args.add(String.valueOf(maxValue));
        Long id = eval(script, Collections.singletonList(key), args);
        return id;
    }
    // ========================= 扩展写法 end ==============================

    // ========================= Script操作 start ==============================

    /**
     * 求值计算
     *
     * @param script
     * @param keys
     * @param args
     * @param <T>
     * @return
     */
    public <T> T eval(String script, List<String> keys, List<String> args) {
        JedisConnection conn = getJedisConnection();
        try {
            return (T) conn.getJedis().eval(script, keys, args);
        } finally {
            conn.close();
        }
    }

    /**
     * 执行脚本，返回执行结果
     *
     * @param script
     * @return
     */
    public String scriptLoad(String script) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().scriptLoad(script);
        } finally {
            conn.close();
        }
    }
    // ========================= Script操作 end ==============================

    // ========================= SortedSet操作 start ==============================

    /**
     * 增加对应的元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean zadd(String key, int score, String value) {
        JedisConnection conn = getJedisConnection();
        try {
            // 0：元素score被更新; 1: 添加成功
            return conn.getJedis().zadd(key, score, value) >= 0;
        } finally {
            conn.close();
        }
    }

    /**
     * 移除对应的元素
     *
     * @param key
     * @param value
     * @return
     */
    public boolean zrem(String key, String value) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().zrem(key, value) >= 0;
        } finally {
            conn.close();
        }
    }

    /**
     * 对有序集合成员做一个score 增加或减少.
     *
     * @param key
     * @param incrStep 可以为负数
     * @param value
     * @return
     */
    public int zincrby(String key, int incrStep, String value) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().zincrby(key, incrStep, value).intValue();
        } finally {
            conn.close();
        }
    }

    /**
     * 对有序集合排序，0, -1返回全部内容
     *
     * @param key
     * @param start
     * @param end
     * @param isDesc true: 降序， false：升序
     * @return
     */
    public Set<String> zsort(String key, int start, int end, boolean isDesc) {
        JedisConnection conn = getJedisConnection();
        try {
            if (isDesc) {
                // 降序
                return conn.getJedis().zrevrange(key, start, end);
            } else {
                // 升序
                return conn.getJedis().zrange(key, start, end);
            }
        } finally {
            conn.close();
        }
    }

    public Set<Tuple> zsortWithScore(String key, int start, int end, boolean isDesc) {
        JedisConnection conn = getJedisConnection();
        try {
            if (isDesc) {
                // 降序
                return conn.getJedis().zrevrangeWithScores(key, start, end);
            } else {
                // 升序
                return conn.getJedis().zrangeWithScores(key, start, end);
            }
        } finally {
            conn.close();
        }
    }

    /**
     * 对score区间排序
     *
     * @param key
     * @param max    最大值
     * @param min    最小值
     * @param isDesc true: 降序， false：升序
     * @return
     */
    public Set<String> zsortByScore(String key, int max, int min, boolean isDesc) {
        JedisConnection conn = getJedisConnection();
        try {
            if (isDesc) {
                // 降序
                return conn.getJedis().zrevrangeByScore(key, max, min);
            } else {
                // 升序
                return conn.getJedis().zrange(key, min, max);
            }
        } finally {
            conn.close();
        }
    }

    /**
     * 获取当前value排名
     *
     * @param key
     * @param value
     * @return
     */
    public long zrank(String key, String value) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().zrank(key, value);
        } finally {
            conn.close();
        }
    }
    // ========================= SortedSet操作 end ==============================

    // ========================= List操作 start ==============================

    /**
     * 虽然可以通过：rpush/lpop：实现队列 （先进先出）； rpush/rpop 实现栈 （先进后出）
     * <p>
     * 但是从目前来说，没有特别适合的业务应用场景。如果redis来做队列，则必然会存在消息丢失的问题。
     * 虽然可以采取一系列的补偿错失，但未免来说有点得不偿失，还不如采用其他高可用的消息队列，如RocketMQ。
     */
    // ========================= List操作 end ================================

    // ========================= Set操作 start ==============================
    public boolean sadd(String key, String value) {
        JedisConnection conn = getJedisConnection();
        try {
            // 0 = 有重复数据，>1 添加成功
            return conn.getJedis().sadd(key, value) >= 0;
        } finally {
            conn.close();
        }
    }

    /**
     * 返回所有成员的差集
     *
     * @param keys
     * @return
     */
    public Set<String> sdiff(String... keys) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().sdiff(keys);
        } finally {
            conn.close();
        }
    }

    /**
     * 返回所有集合的交集
     *
     * @param keys
     * @return
     */
    public Set<String> sinter(String... keys) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().sinter(keys);
        } finally {
            conn.close();
        }
    }

    /**
     * 是否member是否是集合中的成员
     *
     * @return
     */
    public boolean sismember(String key, String member) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().sismember(key, member);
        } finally {
            conn.close();
        }
    }

    /**
     * 返回集合所有成员
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().smembers(key);
        } finally {
            conn.close();
        }
    }

    /**
     * 移除元素
     *
     * @param key
     * @return
     */
    public Set<String> sunion(String key) {
        JedisConnection conn = getJedisConnection();
        try {
            return conn.getJedis().sunion(key);
        } finally {
            conn.close();
        }
    }
    // ========================= Set操作 end ================================
}
