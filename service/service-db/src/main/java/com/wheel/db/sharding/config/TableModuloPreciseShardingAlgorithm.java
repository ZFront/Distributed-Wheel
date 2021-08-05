package com.wheel.db.sharding.config;

import com.wheel.db.sharding.prop.ShardingDbProp;
import com.wheel.db.sharding.utils.ShardingRuleUtil;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * @desc Table 精确分片策略 -- 取模算法
 * @author: zhouf
 */
@Slf4j
public class TableModuloPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Autowired
    private ShardingDbProp shardingDbProp;

    /**
     * 分表公式：value / 表数量 mod 表数量
     *
     * @param tableNames
     * @param shardingValue
     * @return
     */
    @Override
    public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<String> shardingValue) {
        int tableSize = tableNames.size();
        for (Pair<String, Integer> each : ShardingRuleUtil.tableNameToSuffixPair(tableNames)) {
            // 取模算法，需要对其减1
            if ((Integer.parseInt(shardingValue.getValue()) / tableSize % tableSize) == each.getValue() - 1) {
                return each.getKey();
            }
        }
        log.info("Table取模精确分片策略：没找到与分片键匹配的表名! {} : {} = {}", shardingValue.getLogicTableName(), shardingValue.getColumnName(), shardingValue.getValue());
        throw new UnsupportedOperationException();
    }
}
