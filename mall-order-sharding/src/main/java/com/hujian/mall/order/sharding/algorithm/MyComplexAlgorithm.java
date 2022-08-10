package com.hujian.mall.order.sharding.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.ShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * @author hujian
 * @since 2022-08-10 17:17
 */
public class MyComplexAlgorithm implements ComplexKeysShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        Map<String, Collection<String>> valuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        return null;
    }


    /**
     * 例如: SELECT * FROM T_ORDER user_id = 100000 AND order_id = 1000009
     * 循环 获取SQL 中 分片键列对应的value值
     * @param shardingValues sql 中分片键的value值   -> 1000009
     * @param key 分片键列名                        -> user_id
     * @return shardingValues 集合                 -> [1000009]
     */
//    private Collection<Integer> getShardingValue(Collection<ShardingValue> shardingValues, final String key) {
//        Collection<Integer> valueSet = new ArrayList<>();
//        Iterator<ShardingValue> iterator = shardingValues.iterator();
//        while (iterator.hasNext()) {
//            ShardingValue next = iterator.next();
//            if (next instanceof ComplexKeysShardingValue) {
//                ComplexKeysShardingValue value = (ComplexKeysShardingValue) next;
//                // user_id，order_id分片键进行分表
//                if (value.getColumnName().equals(key)) {
//                    return value.getValues();
//                }
//            }
//        }
//        return valueSet;
//    }


    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties properties) {

    }
}
