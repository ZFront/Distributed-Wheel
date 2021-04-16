package com.wheel.redis.extend;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @desc 布隆过滤器
 * 常用于大数量的判断是否存在。
 * 例如：统计UV；爬虫过滤已经抓过的URL；等等
 * <p>
 * 将大批量的数据怼到hash数据中，往往占用的空间会很大。而使用BloomFilter则只需要hash表1/8~1/4大小就解决同样的问题
 * @author: zhouf
 */
public class SingleBloomFilter {

    private static int total = 1000000;

    /**
     * Guava 实现布隆过滤器
     * 其中，fpp(错误率)，默认为0.03
     */
//    private static BloomFilter<Integer> defaultBF = BloomFilter.create(Funnels.integerFunnel(), total);

    private static BloomFilter<Integer> defaultBF = BloomFilter.create(Funnels.integerFunnel(), total, 0.002);

    public static void main(String[] args) {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            defaultBF.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < total; i++) {
            if (!defaultBF.mightContain(i)) {
                System.out.println("未匹配");
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (defaultBF.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);
    }
}
