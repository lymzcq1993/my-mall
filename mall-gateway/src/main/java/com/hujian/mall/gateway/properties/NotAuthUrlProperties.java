package com.hujian.mall.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;

/**
 * @author hujian
 * @since 2022-07-27 15:57
 */
@Data
@ConfigurationProperties("mall.gateway")
public class NotAuthUrlProperties {
    private LinkedHashSet<String> shouldSkipUrls;
}
