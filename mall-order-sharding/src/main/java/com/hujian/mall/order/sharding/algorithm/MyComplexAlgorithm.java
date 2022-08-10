package com.hujian.mall.order.sharding.algorithm;

import lombok.Getter;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.Properties;

/**
 * @author hujian
 * @since 2022-08-10 17:17
 */
public class MyComplexAlgorithm implements ComplexKeysShardingAlgorithm<Long> {
    /**
     * 获取自定义参数
     */
    @Getter
    private Properties props;

    @Override
    public void init(Properties properties) {
        //初始化
        this.props = properties;
    }

    /**
     * 自定义type属性
     * @return
     */
    @Override
    public String getType() {
        return "CUSTOM_COMPLEX";
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {
        System.out.println(shardingValue);
        return null;
    }
}
