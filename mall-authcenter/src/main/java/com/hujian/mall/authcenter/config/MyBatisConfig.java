package com.hujian.mall.authcenter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hujian
 * @since 2022-07-28 09:55
 */
@Configuration(proxyBeanMethods = false)
@MapperScans({
        @MapperScan(value = "com.hujian.mall.mapper.*.mapper")
})
@EnableTransactionManagement
public class MyBatisConfig {
}
