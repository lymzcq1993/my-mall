package com.hujian.mall.authcenter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 保存jwt配置信息
 * @author hujian
*/
@Data
@ConfigurationProperties(prefix = "mall.jwt")
public class JwtCAProperties {

    /**
     * 证书名称
     */
    private String jksPath;


    /**
     * 证书别名
     */
    private String keyPairAlias;

    /**
     * 证书私钥
     */
    private String keyPairSecret;

    /**
     * 证书存储密钥
     */
    private String keyPairStoreSecret;



}
