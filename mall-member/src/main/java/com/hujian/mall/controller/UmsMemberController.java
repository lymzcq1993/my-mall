package com.hujian.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hujian
 * @since 2022-07-21 15:53
 */
@RestController
@Api(tags = "会员登入注册管理")
@RequestMapping("/sso")
@Slf4j
public class UmsMemberController {
    @GetMapping("/register")
    public String register(){
        return "我来用户注册了";
    }
}

