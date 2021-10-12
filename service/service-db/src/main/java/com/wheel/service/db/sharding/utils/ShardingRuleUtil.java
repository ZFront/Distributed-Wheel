package com.wheel.service.db.sharding.utils;

import javafx.util.Pair;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @desc 分片规则工具类
 * @author: zhouf
 */
public class ShardingRuleUtil {

    /**
     * 分割标志，后面的为当前序列号
     * t_order_1等等
     */
    public static String TABLE_FLAG = "_";

    public static String DATABASE_FLAG = "-";

    /**
     * 将数据库/表名 String集合转换为Pair集合
     * 其中value为后缀值
     *
     * @param target
     * @return
     */
    public static Collection<Pair<String, Integer>> tableNameToSuffixPair(Collection<String> target) {
        return target.stream().map(e -> new Pair<>(e, Integer.parseInt(e.substring(e.lastIndexOf(TABLE_FLAG) + 1)))).collect(Collectors.toList());
    }

    public static Collection<Pair<String, Integer>> databaseNameToSuffixPair(Collection<String> target) {
        return target.stream().map(e -> new Pair<>(e, Integer.parseInt(e.substring(e.lastIndexOf(DATABASE_FLAG) + 1)))).collect(Collectors.toList());
    }
}
