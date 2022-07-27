package com.hujian.mall.gateway.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hujian.mall.common.api.ResultCode;
import com.hujian.mall.common.exception.GateWayException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

/**
 * jwk的自定义处理类
 * @author hujian
 * @since 2022-07-27 16:06
 */
@Slf4j
public class JwkUtil {
    /**
     * 认证服务器许可我们的网关的clientId(需要在oauth_client_details表中配置)
     */
    private static final String CLIENT_ID = "mall";

    /**
     * 认证服务器许可我们的网关的client_secret(需要在oauth_client_details表中配置)
     */
    private static final String CLIENT_SECRET = "mall";

    /**
     * 认证服务器暴露的获取token_key的地址
     */
    private static final String AUTH_JWKS_KEY_URL = "http://mall-authcenter/oauth2/jwks";

    /**
     * 请求头中的 token的开始
     */
    private static final String AUTH_HEADER = "Bearer ";
    public static JSONObject validateJwtToken(String authHeader, PublicKey publicKey) {
        String token =null ;
        try{
            token = StrUtil.subAfter(authHeader, AUTH_HEADER, false);
            JWSObject jwsObject = JWSObject.parse(token);
            RSASSAVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
            //校验签名
            boolean verify = jwsObject.verify(verifier);
            //token不正确，可能被篡改
            if (!verify){
                throw new GateWayException(ResultCode.JWT_TOKEN_EXPIRE);
            }
            String jwkStr = jwsObject.getPayload().toString();
            JSONObject claimsJson = JSONUtil.parseObj(jwkStr);
            log.debug(claimsJson.toString());
            return claimsJson;

        }catch(Exception e){

            log.error("校验token异常:{},异常信息:{}",token,e.getMessage());

            throw new GateWayException(ResultCode.JWT_TOKEN_EXPIRE);
        }
    }

    /**
     * 方法实现说明: 通过远程调用获取认证服务器的jwks
     * @author:smlz
     * @param restTemplate 远程调用的操作类
     * @return: tokenKey 解析jwt的tokenKey
     * @exception:
     * @date:2020/1/22 11:31
     */
    private static JSONObject getTokenKeyByRemoteCall(RestTemplate restTemplate) throws GateWayException {

        //第一步:封装请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID,CLIENT_SECRET);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);

        //第二步:远程调用获取token_key
        try {
            ResponseEntity<Map> response = restTemplate.exchange(AUTH_JWKS_KEY_URL, HttpMethod.GET, entity, Map.class);

            List<LinkedHashMap<String,String>> keys = (ArrayList) Objects.requireNonNull(response.getBody()).get("keys");

            log.info("去认证服务器获取Token_Key:{}",keys);

            return JSONUtil.parseObj(keys.get(0));

        }catch (Exception e) {

            log.error("远程调用认证服务器获取Token_Key失败:{}",e.getMessage());

            throw new IllegalAccessError(e.getMessage());
        }
    }



    public static PublicKey genPublicKey(RestTemplate restTemplate) {
        //{
        //	"keys": [
        //		{
        //			"kty": "RSA",
        //			"e": "AQAB",
        //			"kid": "8b593ed8-5840-4fb5-908f-f83b02a65bbd",
        //			"n": "kt1euQs5la91_tFbqqZMeUA82DyJTsJm8Yb7SUOwpXRc1x-VDOQCSPsoZx1c0u7ubvmpNpWoOXs-wawSTrJy1iq8u58X-TmEiwSfKz7QDGUG30BF6WU6qPkvswA4KgRqFZ68q-mlx3fPhBrQC9cOCJ1QQjmuTEgxhNTLvsadkTNVQ-9hFJEp6dowdwPwAFcojar4ciUy0T-A79Mvlzfy9rB4G3GD1feT1bZr_XyOBlz53yJFGmWjorwEANVehPKivjOq-DoYrzvKbzBYwoTKfMP0DAT0E4HdyIaBsen_jnwm9_RiCUWkNkJZuI7sLGLuClWGpUY5wJIu57UQPwfBbw"
        //		}
        //	]
        //}
        JSONObject jwkJsonObj = getTokenKeyByRemoteCall(restTemplate);
        try{

            //把获取的公钥开头和结尾替换掉
            RSAKey rsaKey = RSAKey.parse(jwkJsonObj.toString());
            return rsaKey.toRSAPublicKey();

        }catch (Exception e) {
            log.info("生成公钥异常:{}",e.getMessage());
            throw new GateWayException(ResultCode.GEN_PUBLIC_KEY_ERROR);
        }

    }
}
