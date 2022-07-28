package com.hujian.mall.search.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hujian
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ESProperties {
    private String host;
    private int port;
    private String user;
    private String pwd;
    private int connTimeout;
    private int socketTimeout;
    private int connectionRequestTimeout;
}
