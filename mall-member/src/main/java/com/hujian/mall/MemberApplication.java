package com.hujian.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 可以省略EnableDiscoveryClient注解，这里为了规范还是写上
 * @author hujian
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MemberApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MemberApplication.class, args);
	}

}
