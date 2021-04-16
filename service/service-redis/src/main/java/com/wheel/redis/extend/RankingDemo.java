package com.wheel.redis.extend;

import com.wheel.common.util.AmountUtil;
import com.wheel.common.vo.PageQuery;
import com.wheel.redis.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @desc 排行榜例子
 * @author: zhouf
 */
@Component
public class RankingDemo {

    private static String SORT_KEY = "ranking";

    @Autowired
    private RedisClient redisClient;

    public void initRanking() {
        redisClient.zadd(SORT_KEY, 0, "Tom");
        redisClient.zadd(SORT_KEY, 0, "Jack");
        redisClient.zadd(SORT_KEY, 0, "Tony");
        redisClient.zadd(SORT_KEY, 0, "Jo");
        redisClient.zadd(SORT_KEY, 0, "Windy");
    }

    /**
     * 改变分数
     *
     * @param point 分数，可以为服务
     * @param name  实际业务中，一般为用户的唯一ID，等等
     */
    public void changePoint(int point, String name) {
        redisClient.zincrby(SORT_KEY, point, name);
    }

    /**
     * 添加用户
     *
     * @param score
     * @param name
     */
    public boolean addUser(int score, String name) {
        return redisClient.zadd(SORT_KEY, score, name);
    }

    /**
     * 移除排行榜
     *
     * @param name
     * @return
     */
    public boolean delUser(String name) {
        return redisClient.zrem(SORT_KEY, name);
    }

    /**
     * 获取当前排名
     *
     * @param name
     * @return
     */
    public long getUserRank(String name) {
        return redisClient.zrank(SORT_KEY, name);
    }

    /**
     * 获取排行榜信息
     *
     * @param pageQuery
     * @param isDesc
     * @return
     */
    public Set<String> rankingList(PageQuery pageQuery, boolean isDesc) {
        double start = AmountUtil.mul(pageQuery.getPageCurrent() - 1, pageQuery.getPageSize());
        double end = AmountUtil.mul(pageQuery.getPageCurrent(), pageQuery.getPageSize()) - 1;
        return redisClient.zsort(SORT_KEY, (int) start, (int) end, isDesc);
    }

    public Set<Tuple> rankingScoreList(PageQuery pageQuery, boolean isDesc) {
        double start = AmountUtil.mul(pageQuery.getPageCurrent() - 1, pageQuery.getPageSize());
        double end = AmountUtil.mul(pageQuery.getPageCurrent(), pageQuery.getPageSize()) - 1;
        return redisClient.zsortWithScore(SORT_KEY, (int) start, (int) end, isDesc);
    }
}
