package com.hujian.mall.gateway.filter;

import cn.hutool.json.JSONObject;
import com.hujian.mall.common.api.ResultCode;
import com.hujian.mall.common.exception.GateWayException;
import com.hujian.mall.gateway.properties.NotAuthUrlProperties;
import com.hujian.mall.gateway.utils.JwkUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;

/**
 * 鉴权
 * @author hujian
 * @since 2022-07-27 15:53
 */
@Component
@Slf4j
@Order(0)
@EnableConfigurationProperties(value = NotAuthUrlProperties.class)
public class AuthorizationFilter implements GlobalFilter, InitializingBean {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 请求各个微服务 不需要用户认证的URL
     */
    @Autowired
    private NotAuthUrlProperties notAuthUrlProperties;

    /**
     * jwk公钥，在应用启动的时候去认证中心获取
     */
    private PublicKey publicKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.跳过不需要认证的url
        String currentUrl = exchange.getRequest().getURI().getPath();
        if(shouldSkip(currentUrl)) {
            //log.info("跳过认证的URL:{}",currentUrl);
            return chain.filter(exchange);
        }
        //2.解析出我们Authorization的请求头  value为: “bearer XXXXXXXXXXXXXX”
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        //3.判断Authorization的请求头是否为空
        if(StringUtils.isEmpty(authHeader)) {
            log.warn("需要认证的url,请求头为空");
            throw new GateWayException(ResultCode.AUTHORIZATION_HEADER_IS_EMPTY);
        }
        //4.校验jwk 若jwt不对或者超时都会抛出异常
        JSONObject claimJson = JwkUtil.validateJwtToken(authHeader, publicKey);

        //5.解析jwt，将用户信息传递到header中
        ServerWebExchange webExchange = wrapHeader(exchange,claimJson);
        return chain.filter(webExchange);
    }

    private ServerWebExchange wrapHeader(ServerWebExchange exchange, JSONObject claimJson) {
        String memberId = claimJson.getStr("memberId");

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("memberId", memberId)
                .build();
        return exchange.mutate().request(request).build();
    }

    /**
     * 方法实现说明:不需要授权的路径
     * @author:smlz
     * @param currentUrl 当前请求路径
     * @return:
     * @exception:
     * @date:2019/12/26 13:49
     */
    private boolean shouldSkip(String currentUrl) {
        //路径匹配器(简介SpringMvc拦截器的匹配器)
        //比如/oauth/** 可以匹配/oauth/token    /oauth/check_token等
        PathMatcher pathMatcher = new AntPathMatcher();
        for(String skipPath:notAuthUrlProperties.getShouldSkipUrls()) {
            if(pathMatcher.match(skipPath,currentUrl)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 这里restTemplate需要提前手动注入
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化公钥
        this.publicKey = JwkUtil.genPublicKey(restTemplate);
    }
}
