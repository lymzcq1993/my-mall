package com.hujian.mall.order.sharding.algorithm;

import lombok.Getter;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

/**
 * @author hujian
 * @since 2022-08-10 17:17
 */
public class MyAlgorithm implements StandardShardingAlgorithm<Long> {
    /**
     * 获取自定义参数
     */
    @Getter
    Properties props;

    @Override
    public String doSharding(Collection<String> databaseNames, PreciseShardingValue<Long> shardingValue) {


        Long value = shardingValue.getValue();
        //分片策略
        Long shardMod = value % 2;

        for (String database : databaseNames) {
            if (database.endsWith(String.valueOf(shardMod))) {
                return database;
            }
        }

        return "";
    }

    /**
     * 范围分片
     * @param collection
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        String columnName = rangeShardingValue.getColumnName();
        return null;
    }

    @Override
    public void init(Properties properties) {
        //初始化
        this.props = properties;
    }

    @Override
    public String getType() {
        return "CUSTOM_SHARED";
    }

}
