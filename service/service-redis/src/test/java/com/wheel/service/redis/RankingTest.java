package com.wheel.service.redis;

import com.wheel.common.util.JsonUtil;
import com.wheel.common.vo.PageQuery;
import com.wheel.service.redis.extend.RankingDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desc
 * @author: zhouf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceRedisApp.class)
public class RankingTest {

    @Autowired
    private RankingDemo rankingDemo;

    @Test
    public void testInit(){
        // 初始化排行榜
        rankingDemo.initRanking();
    }

    @Test
    public void testRanking() {

        PageQuery pageQuery = PageQuery.newInstance(1, 10);

        // 倒序排序
        System.out.println(JsonUtil.toString(rankingDemo.rankingScoreList(pageQuery, true)));

        // 添加
        rankingDemo.addUser(10, "Buddy");

        // 修改分数
        rankingDemo.changePoint(5, "Jo");

        // 获取排名
        System.out.println(rankingDemo.getUserRank("Jo"));

        // 倒序排序
        System.out.println(JsonUtil.toString(rankingDemo.rankingScoreList(pageQuery, true)));

        // 升序排序
        System.out.println(JsonUtil.toString(rankingDemo.rankingScoreList(pageQuery, false)));
    }
}
