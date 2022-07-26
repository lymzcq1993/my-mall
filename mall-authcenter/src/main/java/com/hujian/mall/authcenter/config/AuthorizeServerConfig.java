package com.hujian.mall.authcenter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujian.mall.authcenter.domain.MemberDetail;
import com.hujian.mall.authcenter.properties.JwtCAProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.authentication.OAuth2AuthenticationContext;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

/**
 * @author hujian
 * @since 2022-07-26 16:11
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
@EnableConfigurationProperties(JwtCAProperties.class)
public class AuthorizeServerConfig {
    @Autowired
    private JwtCAProperties jwtCAProperties;

    /**
     * 重定向设置
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();
        http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);


        //未通过身份验证时重定向到登录页面授权端点
        http.exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login"))
        );
        return http.build();
    }


    /**
     * 对token进行增强
     * 参考https://docs.spring.io/spring-authorization-server/docs/current/reference/html/guides/how-to-userinfo.html#how-to-userinfo
     * @return
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
//            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
//                OidcUserInfo userInfo = userInfoService.loadUser(
//                        context.getPrincipal().getName());
//                context.getClaims().claims(claims ->
//                        claims.putAll(userInfo.getClaims()));
//            }
            MemberDetail user = (MemberDetail) context.getPrincipal().getPrincipal();
            context.getClaims().claims(
                    claims ->{
                        claims.put("memberId",user.getUmsAdmin().getId());
                    }
            );
        };
    }

    /**
     * 使用默认配置进行表单登入
     * @param http
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                //表单登录处理从授权服务器过滤器链
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    /**
     * 授权服务：管理OAuth2授权信息服务
     * 脚本在org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate
            , RegisteredClientRepository registeredClientRepository
            , ObjectMapper objectMapper) {
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);
        rowMapper.setObjectMapper(objectMapper);
        JdbcOAuth2AuthorizationService auth2AuthorizationService = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        auth2AuthorizationService.setAuthorizationRowMapper(rowMapper);
        return auth2AuthorizationService;
    }

    /**
     * 授权确认信息处理服务
     * 脚本在org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * 使用JDBC存储客户端应用
     * 脚本在 org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql
     * 这里我已经在数据库中存了client-id 和client-secret  mall : mall的client
     * @return
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        //内存模式，可以手动配置然后save到数据库中
        //        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                //认证客户端id
//                .clientId("mall")
//                //客户端密码 {noop}是配置明文模式
//                .clientSecret("{noop}mall")
//                //四种认证模式
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                //回调链接，可以配置多个，非以下回调的地址的全部拒绝
//                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
//                .redirectUri("http://www.baidu.com")
//                .scope(OidcScopes.OPENID)
//                .scope("message.read")
//                .scope("message.write")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .tokenSettings(TokenSettings.builder()
//                        //token有效期
//                        .accessTokenTimeToLive(Duration.ofMinutes(100))
//                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
//                        .build())
//                .build();
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }


    /**
     * 加载JWK资源
     * JWT：指的是 JSON Web Token，不存在签名的JWT是不安全的，存在签名的JWT是不可窜改的
     * JWS：指的是签过名的JWT，即拥有签名的JWT
     * JWK：既然涉及到签名，就涉及到签名算法，对称加密还是非对称加密，那么就需要加密的 密钥或者公私钥对。此处我们将 JWT的密钥或者公私钥对统一称为 JSON WEB KEY，即 JWK。
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
//        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        log.debug("public key:{}",publicKey);
        log.debug("private key:{}",privateKey);
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        List<JWK> keys = jwkSet.getKeys();
        return new ImmutableJWKSet<>(jwkSet);
    }



    /**
     * 生成密钥对,启动时生成的带有密钥的实例java.security.KeyPair用于创建JWKSource上述内容
     * @return
     */
//    private static KeyPair generateRsaKey() {
//        KeyPair keyPair;
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(AsymmetricAlgorithm.RSA.getValue());
//            keyPairGenerator.initialize(2048);
//            keyPair = keyPairGenerator.generateKeyPair();
//        }
//        catch (Exception ex) {
//            throw new IllegalStateException(ex);
//        }
//        return keyPair;
//    }

    @Bean
    public KeyPair keyPair() throws FileNotFoundException {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(jwtCAProperties.getJksPath())
                ,jwtCAProperties.getKeyPairStoreSecret().toCharArray()
        );
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(jwtCAProperties.getKeyPairAlias()
                , jwtCAProperties.getKeyPairSecret().toCharArray());
        RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();
        return keyPair;
    }

    /**
     * ProviderSettings配置 Spring Authorization Server的实例
     * @return
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().build();
    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
}
