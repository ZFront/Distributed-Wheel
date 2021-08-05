package com.wheel.db.sharding.config;

import com.wheel.common.exception.BizException;
import com.wheel.db.sharding.utils.ShardingRuleUtil;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;


/**
 * @desc Database 精确分片策略 -- 取模算法
 * @author: zhouf
 */
@Slf4j
public class DatabaseModuloPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    /**
     * 分库的公式为：value mod 库数量
     *
     * @param databaseNames 是conf中定义的数据库别名
     * @param shardingValue
     * @return
     */
    @Override
    public String doSharding(final Collection<String> databaseNames, final PreciseShardingValue<String> shardingValue) {
        for (Pair<String, Integer> each : ShardingRuleUtil.databaseNameToSuffixPair(databaseNames)) {
            if ((Integer.parseInt(shardingValue.getValue()) % databaseNames.size()) == each.getValue() - 1) {
                // 取模算法，需要对其减1
                return each.getKey();
            }
        }

        log.error("Database取模精确分片策略：没找到与分片键匹配的库名! {} : {} = {}", shardingValue.getLogicTableName(), shardingValue.getColumnName(), shardingValue.getValue());
        throw new BizException("未支持的分库方式");
    }
}
