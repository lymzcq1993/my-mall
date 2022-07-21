package com.hujian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 可以省略EnableDiscoveryClient注解，这里为了规范还是写上
 * @author hujian
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

}
