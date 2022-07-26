package com.hujian.mall.authcenter.config;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author hujian
 */
@Configuration
@EnableWebSecurity
public class ResourceConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
//        // 将密码加密方式采用委托方式，默认以BCryptPasswordEncoder方式进行加密，兼容ldap,MD4,MD5等方式
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //测试使用明文方式
        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * 使用WebSecurity.ignoring()忽略某些URL请求，这些请求将被Spring Security忽略
     * 因为认证服务器在一个服务，所以这里需要放行oauth2请求
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                // 忽略地址拦截
                web.ignoring().antMatchers(ArrayUtil.toArray(ListUtil.toList("oauth2/**"),String.class));
            }
        };
    }


    /**
     * 针对http请求，进行拦截过滤
     *
     * CookieCsrfTokenRepository进行CSRF保护的工作方式：
     *      1.客户端向服务器发出GET请求，例如请求主页
     *      2.Spring发送 GET 请求的响应以及 Set-cookie 标头，其中包含安全生成的XSRF令牌
     */
    @Bean
    public SecurityFilterChain httpSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.antMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                )

                //使用默认登录页面
                //.formLogin(withDefaults())

                //设置form登录，设置且放开登录页login
                .formLogin(fromlogin -> fromlogin.loginPage("/login").permitAll())

                // Spring Security CSRF保护
                .csrf(csrfToken -> csrfToken.csrfTokenRepository(new CookieCsrfTokenRepository()))

//                 //开启认证服务器的资源服务器相关功能，即需校验token
//                .oauth2ResourceServer()
//                .accessDeniedHandler(new SimpleAccessDeniedHandler())
//                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
//                .jwt()
        ;
        return httpSecurity.build();
    }
}
