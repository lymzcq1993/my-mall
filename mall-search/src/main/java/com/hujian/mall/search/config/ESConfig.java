package com.hujian.mall.search.config;

import com.hujian.mall.search.properties.ESProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hujian
 */
@Configuration
@EnableConfigurationProperties(ESProperties.class)
public class ESConfig {
    @Autowired
    private ESProperties esProperties;

    /**
     * 设置的用户名密码不会使用，
     * 实际需要使用BASIC AUTH来进行认证
     * @return
     */
    @Bean(destroyMethod = "close", name = "client")
    public RestHighLevelClient initRestClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(esProperties.getHost(), esProperties.getPort()))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(esProperties.getConnTimeout())
                        .setSocketTimeout(esProperties.getSocketTimeout())
                        .setConnectionRequestTimeout(esProperties.getConnectionRequestTimeout()));
        return new RestHighLevelClient(builder);
    }
}
