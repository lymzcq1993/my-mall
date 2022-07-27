package com.hujian.mall.gateway.config;

import cn.hutool.core.collection.ListUtil;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.swing.plaf.ListUI;

/**
 * 这里为了防止在afterPropertiesSet的时候使用restTemplate还没有设置拦截
 * 所以需要提前设置拦截器
 * SmartInitializingSingleton在所有bean实例化之后执行
 * @author hujian
 * @since 2022-07-27 16:31
 */
@Configuration(proxyBeanMethods = false)
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(LoadBalancerInterceptor loadBalancerInterceptor){
        RestTemplate template = new RestTemplate();
        template.setInterceptors(ListUtil.toList(loadBalancerInterceptor));
        return template;
    }
}
